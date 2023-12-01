package co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.entidad;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Token {
    @Id
    @Column(name = "id_token")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idToken;
    private String token;
    private boolean revocado;
    private boolean expirado;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
