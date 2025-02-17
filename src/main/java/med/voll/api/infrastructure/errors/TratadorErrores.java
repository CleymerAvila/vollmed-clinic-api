package med.voll.api.infrastructure.errors;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

import med.voll.api.infrastructure.errors.exception.ValidacionException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DatosErrorValidacion>> tratarError400(MethodArgumentNotValidException e){

        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }


    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<String> tratarErrorValidacion(ValidacionException e){

        return ResponseEntity.badRequest().body(e.getMessage());
    }


    private record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }

    }

}
