package management.sttock.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<ErrorResponse> handleBadReqException(ValidateException e) {
        return ResponseEntity.status(e.getStatus()).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ErrorResponse> handleBadReqException(TokenRefreshException e) {
        return ResponseEntity.status(403).body(new ErrorResponse(e.getMessage()));
    }

}
