package management.sttock.support.error;

import lombok.extern.slf4j.Slf4j;
import management.sttock.support.error.common.CommonApiException;
import management.sttock.support.error.common.CommonErrorType;
import management.sttock.support.error.product.ProductApiException;
import management.sttock.support.error.user.UserApiException;
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
        return ResponseEntity.badRequest().body(response.updateErrorResponse(CommonErrorType.BAD_REQUEST_DATA));
    }

    @ExceptionHandler(CommonApiException.class)
    public ResponseEntity<Object> handleCommonApiException(CommonApiException ex) {
        log.error("CommonApiException: {}", ex);
        return ResponseEntity.status(ex.getErrorType().getStatus()).body(response.updateErrorResponse(ex.getErrorType()));
    }

    @ExceptionHandler(ProductApiException.class)
    public ResponseEntity<Object> handleProductApiException(ProductApiException ex) {
        log.error("ProductApiException: {}", ex);
        return ResponseEntity.status(ex.getErrorType().getStatus()).body(response.updateErrorResponse(ex.getErrorType()));
    }

    @ExceptionHandler(UserApiException.class)
    public ResponseEntity<Object> handleProductApiException(UserApiException ex) {
        log.error("UserApiException: {}", ex);
        return ResponseEntity.status(ex.getErrorType().getStatus()).body(response.updateErrorResponse(ex.getErrorType()));
    }
}