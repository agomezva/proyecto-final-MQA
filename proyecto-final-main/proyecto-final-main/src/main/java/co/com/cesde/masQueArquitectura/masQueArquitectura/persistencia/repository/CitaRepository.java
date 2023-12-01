package co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.repository;

import co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.entidad.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface CitaRepository extends JpaRepository<Cita,Integer> {

    Optional<List<Cita>> findByIdUsuario(Integer idUsuario);

    List<Cita> findByFechaCita(LocalDate fecha);

    Optional<Cita> findByFechaCitaAndHoraInicio(LocalDate fechaCita, LocalTime horaInicio);

}
