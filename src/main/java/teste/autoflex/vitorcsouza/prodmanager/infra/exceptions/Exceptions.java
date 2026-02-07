package teste.autoflex.vitorcsouza.prodmanager.infra.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class Exceptions {

    // 404 - Not found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseError> handleNoSuchElement(EntityNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 400 - Bad request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        return buildResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseError> handleBusiness(BusinessException ex) {
        String message = ex.getMessage();
        return buildResponse(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> handleBusiness(IllegalArgumentException ex) {
        String message = ex.getMessage();
        return buildResponse(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Schema(description = "Standard error response")
    private ResponseEntity<ResponseError> buildResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(new ResponseError(message, status, LocalDateTime.now()));
    }
}
