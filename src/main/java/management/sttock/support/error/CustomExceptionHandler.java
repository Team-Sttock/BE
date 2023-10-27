package management.sttock.support.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    ErrorResponse response = new ErrorResponse();
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("MethodArgumentNotValidException: {}", ex);
        return ResponseEntity.badRequest().body(response.updateErrorResponse(ErrorType.BAD_REQUEST_DATA));
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleCommonApiException(ApiException ex) {
        log.error("ApiException: {}", ex);
        return ResponseEntity.status(ex.getErrorType().getStatus()).body(response.updateErrorResponse(ex.getErrorType()));
    }
}