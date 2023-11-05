package management.sttock.support.error;

import javax.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    ErrorResponse response = new ErrorResponse();

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        log.error("MissingServletRequestParameterException: {}", ex);
        return ResponseEntity.badRequest().body(response.updateErrorResponse(ErrorType.BAD_REQUEST_DATA));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("MethodArgumentNotValidException: {}", ex);
        return ResponseEntity.badRequest().body(response.updateErrorResponse(ErrorType.BAD_REQUEST_DATA));
    }
    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity handleUnexpectedException(UnexpectedTypeException ex){
        log.error("UnexpectedTypeException: {}", ex);
        ApiException apiException = new ApiException(ErrorType.BAD_REQUEST_DATA);
        return ResponseEntity.status(apiException.getErrorType().getStatus()).body(response.updateErrorResponse(apiException.getErrorType()));
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity handleCommonApiException(ApiException ex) {
        log.error("ApiException: {}", ex);
        return ResponseEntity.status(ex.getErrorType().getStatus()).body(response.updateErrorResponse(ex.getErrorType()));
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handleNullPointerException(NullPointerException ex){
        log.error("NullPointerException:{}", ex);
        ApiException apiException = new ApiException(ErrorType.UNAUTHENTICATED_STATUS);
        return ResponseEntity.status(apiException.getErrorType().getStatus()).body(response.updateErrorResponse(apiException.getErrorType()));
    }
    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity handleInternalServerErrorException(InternalServerError ex){
        log.error("InternalServerError:{}",ex);
        ApiException apiException = new ApiException(ErrorType.SERVER_ERROR);
        return ResponseEntity.status(apiException.getErrorType().getStatus()).body(response.updateErrorResponse(apiException.getErrorType()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        log.error("Exception:{}", ex);
        ApiException apiException = new ApiException(ErrorType.SERVER_ERROR);
        return ResponseEntity.status(apiException.getErrorType().getStatus()).body(response.updateErrorResponse(apiException.getErrorType()));
    }
}