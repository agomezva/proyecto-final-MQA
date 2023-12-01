package co.com.cesde.masQueArquitectura.masQueArquitectura.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaDto {
    private Integer idCita;
    private Integer idUsuario;
    private Integer idCliente;
    private String fecha;
    private String horaInicio;

}
