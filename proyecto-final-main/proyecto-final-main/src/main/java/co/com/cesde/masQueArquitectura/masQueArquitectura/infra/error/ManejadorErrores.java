package co.com.cesde.masQueArquitectura.masQueArquitectura.infra.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;

@RestControllerAdvice
public class ManejadorErrores {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleErrorValidation(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
