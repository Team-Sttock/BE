package management.sttock.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidateException extends RuntimeException{
    private final HttpStatus status;
    private final String message;
    public ValidateException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
