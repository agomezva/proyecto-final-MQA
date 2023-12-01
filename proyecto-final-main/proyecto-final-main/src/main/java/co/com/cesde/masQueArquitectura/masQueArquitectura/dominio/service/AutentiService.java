package co.com.cesde.masQueArquitectura.masQueArquitectura.dominio.service;

import co.com.cesde.masQueArquitectura.masQueArquitectura.config.JwtService;
import co.com.cesde.masQueArquitectura.masQueArquitectura.dto.DatosLogin;
import co.com.cesde.masQueArquitectura.masQueArquitectura.dto.DatosRegistro;
import co.com.cesde.masQueArquitectura.masQueArquitectura.dto.RespuestaAutenticacion;
import co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.entidad.Tipo;
import co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.entidad.Token;
import co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.entidad.Usuario;
import co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.repository.TokenRepository;
import co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutentiService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RespuestaAutenticacion login(DatosLogin datosLogin) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        datosLogin.getEmail(),
                        datosLogin.getContrasena()
                )
        );

        var usuario = usuarioRepository.findUsuarioByEmail(datosLogin.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(usuario);
        removerTodosLosTokensUsuario(usuario);
        guardarUsuarioToken(usuario, jwtToken);
        return RespuestaAutenticacion.builder()
                .token(jwtToken).build();
    }


    public RespuestaAutenticacion registro(DatosRegistro datosRegistro) {

        try {
            if(usuarioRepository.findUsuarioByEmail(datosRegistro.getEmail()).isEmpty()){
                var usuario = Usuario.builder()
                        .nombre(datosRegistro.getNombre())
                        .apellido(datosRegistro.getApellido())
                        .telefono(datosRegistro.getTelefono())
                        .email(datosRegistro.getEmail())
                        .contrasena(passwordEncoder.encode(datosRegistro.getContrasena()))
                        .tipo(Tipo.ARQUITECTO)
                        .build();

                var usuarioGuardado = usuarioRepository.save(usuario);
                var jwtToken = jwtService.generateToken(usuario);
                guardarUsuarioToken(usuarioGuardado, jwtToken);
                return RespuestaAutenticacion.builder()
                        .token(jwtToken).build();
            }
            throw new ValidationException("Usuario con este email ya existe");
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }

    }


    private void removerTodosLosTokensUsuario(Usuario usuario) {
        var tokensUsuarioValidados = tokenRepository.findAllValidTokensByUser(usuario.getIdUsuario());
        if (tokensUsuarioValidados.isEmpty()) {
            return;
        }

        tokensUsuarioValidados.forEach(t ->
                {
                    t.setExpirado(true);
                    t.setRevocado(true);
                }
        );

        tokenRepository.saveAll(tokensUsuarioValidados);
    }


    private void guardarUsuarioToken(Usuario usuarioGuardado, String jwtToken) {
        var token = Token.builder()
                .usuario(usuarioGuardado)
                .token(jwtToken)
                .expirado(false)
                .revocado(false)
                .build();

        tokenRepository.save(token);
    }

}

