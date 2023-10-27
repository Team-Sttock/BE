package management.sttock.support.error;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private ErrorType errorType;

    public ApiException(ErrorType errorType) {
        this.errorType = errorType;
    }
}