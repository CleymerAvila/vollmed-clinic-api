package med.voll.api.infrastructure.errors.exception;


public class ValidacionException extends RuntimeException{

    public ValidacionException(String mensaje){
        super(mensaje);
    }
}
