package co.com.cesde.masQueArquitectura.masQueArquitectura.controllers;

import co.com.cesde.masQueArquitectura.masQueArquitectura.dominio.service.CotizacionService;
import co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.entidad.Cotizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cotizacion")
public class CotizacionController {


    @Autowired
    private CotizacionService cotizacionService;


    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public ResponseEntity<String> save(@RequestBody Cotizacion cotizacion){
            cotizacionService.save(cotizacion);
            return ResponseEntity.ok("La cotizacion fue creada");

    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Cotizacion> findById(@PathVariable(value = "id") Integer id){
        Optional<Cotizacion> cotizacionExiste = cotizacionService.findById(id);

        return cotizacionExiste.map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping(value = "/eliminar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value = "id")Integer id){
        try{
            cotizacionService.deleteById(id);
            return ResponseEntity.ok("La cotizacion fue eliminada");
        }catch (EmptyResultDataAccessException error){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se enconttro ninguna cotizacion por el Id enviado");
        }
    }

}
