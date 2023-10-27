package management.sttock.support.error.common;

import lombok.Getter;

@Getter
public class CommonApiException extends RuntimeException {
    private CommonErrorType errorType;

    public CommonApiException(CommonErrorType errorType) {
        this.errorType = errorType;
    }
}