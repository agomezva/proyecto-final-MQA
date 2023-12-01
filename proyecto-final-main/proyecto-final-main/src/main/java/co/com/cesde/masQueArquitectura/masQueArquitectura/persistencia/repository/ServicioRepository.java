package co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.repository;

import co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.entidad.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicioRepository extends JpaRepository<Servicio,Integer> {


    Optional<Servicio> findByNombre(String nombre);

}
