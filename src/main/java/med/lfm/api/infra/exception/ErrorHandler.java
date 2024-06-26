package med.lfm.api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import med.lfm.api.domain.ValidationException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handle404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle400(MethodArgumentNotValidException e) {
        var errors = e.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(error400DTO::new).toList());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handeBusinessRules(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    private record error400DTO(String field, String message) {
        public error400DTO(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
