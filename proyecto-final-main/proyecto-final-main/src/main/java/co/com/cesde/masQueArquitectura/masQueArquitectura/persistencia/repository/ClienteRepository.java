package co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.repository;

import co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.entidad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {


}
