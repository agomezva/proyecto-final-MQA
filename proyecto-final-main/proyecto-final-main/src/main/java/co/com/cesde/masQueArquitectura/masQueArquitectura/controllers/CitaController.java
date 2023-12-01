package co.com.cesde.masQueArquitectura.masQueArquitectura.controllers;

import co.com.cesde.masQueArquitectura.masQueArquitectura.dominio.service.CitaService;
import co.com.cesde.masQueArquitectura.masQueArquitectura.dto.CitaDto;
import co.com.cesde.masQueArquitectura.masQueArquitectura.persistencia.entidad.Cita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cita")
public class CitaController {

    @Autowired
    private CitaService citaService;


    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public ResponseEntity<String> save(@RequestBody CitaDto citaDto) throws ValidationException {
        Cita cita = new Cita(citaDto);
        Optional<Cita> citaExiste = citaService.findByDateAndTime(cita.getFechaCita(),cita.getHoraInicio());
        if (citaExiste.isPresent()){
            throw new ValidationException("El horario o fecha no esta disponible");
        }else{
            citaService.save(cita);
            return ResponseEntity.ok("La cita fue agendada");
        }
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@RequestBody CitaDto citaDto) throws ValidationException {

        Optional<Cita> citaExiste = citaService.findById(citaDto.getIdCita());
        LocalTime hora = LocalTime.parse(citaDto.getHoraInicio());
        LocalDate fecha = LocalDate.parse(citaDto.getFecha());
        if(citaExiste.isPresent()){
            Optional<Cita> posibleCita = citaService.findByDateAndTime(fecha, hora);
           if(posibleCita.isPresent()){
                throw new ValidationException("La fecha y hora seleccionadas ya estan ocupadas");
            }
            Cita cita = citaExiste.get();

            cita.setIdUsuario(citaDto.getIdUsuario());
            cita.setIdCliente(citaDto.getIdCliente());
            cita.setFechaCita(fecha);
            cita.setHoraInicio(hora);
            cita.setHoraFinal(hora.plusHours(2));

            citaService.save(cita);

            return ResponseEntity.ok("La cita fue modificada");
        }else{
            throw new ValidationException("La cita a modificar no existe");
        }
    }

    @DeleteMapping(value = "/eliminar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") Integer id) {
        try {
            citaService.deleteById(id);
            return ResponseEntity.ok("La cita ha sido eliminada");
        }catch (EmptyResultDataAccessException error){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontre ninguna cita con el Id enviado");

        }
    }

    @PostMapping("/fecha")
    public ResponseEntity<List<Cita>> getByDate(@RequestBody CitaDto citaDto){
        LocalDate fechaConvertida = LocalDate.parse(citaDto.getFecha());
        System.out.println(fechaConvertida);
        List<Cita> citas = citaService.getByDate(fechaConvertida);

        if(citas.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(citas);
        }
    }

    @GetMapping(value = "/buscarArquitecto/{id}")
    public Optional<List<Cita>> findByIdArquitecto(Integer idArquitecto){

        return citaService.findByIdArquitecto(idArquitecto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> findById(@PathVariable(value = "id") Integer id){
        Optional<Cita> citaExiate = citaService.findById(id);

        return citaExiate.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @GetMapping(value = "/listar")
    public List<Cita> listarCitas(){
        return citaService.getAll();
    }


}
