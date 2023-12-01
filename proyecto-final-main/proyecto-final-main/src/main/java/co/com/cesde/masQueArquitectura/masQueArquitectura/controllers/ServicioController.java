package co.com.cesde.masQueArquitectura.masQueArquitectura.controllers;


import co.com.cesde.masQueArquitectura.masQueArquitectura.dominio.service.ServicioService;
import co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.entidad.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/servicio")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping(value = "/listar")
    public List<Servicio> listarServicio(){
        return servicioService.getAll();
    }

    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public ResponseEntity<String> save(@RequestBody Servicio servicio){
        Optional<Servicio> servicioExiste = servicioService.findByName(servicio.getNombre());
        if (servicioExiste.isPresent()){
            Servicio servicioAmodificar = servicioExiste.get();
            servicioAmodificar.setNombre(servicio.getNombre());
            servicioAmodificar.setDescripcion(servicio.getDescripcion());

            servicioService.save(servicioAmodificar);
            return ResponseEntity.ok("El servicio fue modificado");
        }else{
            servicioService.save(servicio);
            return ResponseEntity.ok("El servicio fue creado");
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Servicio> findById(@PathVariable(value = "id")Integer id){
        Optional<Servicio> servicioExiste = servicioService.findById(id);

        return servicioExiste.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping(value = "/eliminar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value = "id")Integer id){
        try{
            servicioService.deleteById(id);
            return ResponseEntity.ok("El servicio ha sido eliminado");
        }catch (EmptyResultDataAccessException error){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro ningun servicio por el Id enviado");
        }
    }
}

