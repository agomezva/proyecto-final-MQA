package co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.entidad;

import co.com.cesde.masQueArquitectura.masQueArquitectura.dto.CitaDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Cita {

    @Id
    @Column(name = "id_cita")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCita;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "fecha_cita")
    private LocalDate fechaCita;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_final")
    private LocalTime horaFinal;

    @ManyToOne
    @JoinColumn(name = "id_usuario",insertable = false,updatable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_cliente",insertable = false,updatable = false)
    private Cliente cliente;

    public Cita(CitaDto citaDto){
        this.setIdCita(citaDto.getIdCita());
        this.setIdUsuario(citaDto.getIdUsuario());
        this.setIdCliente(citaDto.getIdCliente());
        LocalDate fecha = LocalDate.parse(citaDto.getFecha());
        this.setFechaCita(fecha);
        LocalTime horaInicio = LocalTime.parse(citaDto.getHoraInicio());
        this.setHoraInicio(horaInicio);
        this.setHoraFinal(horaInicio.plusHours(2));
    }

}