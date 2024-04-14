package med.lfm.api.domain;

public class ValidationException extends RuntimeException {
    
    public ValidationException(String message) {
        super(message);
    }
}
