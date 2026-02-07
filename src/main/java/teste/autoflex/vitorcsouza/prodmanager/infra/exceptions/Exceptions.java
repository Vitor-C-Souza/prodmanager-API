package teste.autoflex.vitorcsouza.prodmanager.infra.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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

    // 409 - Conflict
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseError> handleBusiness(BusinessException ex) {
        String message = ex.getMessage();
        return buildResponse(message, HttpStatus.CONFLICT);
    }

    // 422 - Unprocessable entity
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> handleBusiness(IllegalArgumentException ex) {
        String message = ex.getMessage();
        return buildResponse(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // 409 - Conflict
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ResponseError> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // 401 - Unauthorized
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseError> handleBadCredentials(BadCredentialsException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // 500 - Internal server error
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseError> handleRuntime(RuntimeException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Schema(description = "Standard error response")
    private ResponseEntity<ResponseError> buildResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(new ResponseError(message, status, LocalDateTime.now()));
    }
}
