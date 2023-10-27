package management.sttock.support.error.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum CommonErrorType {
    BAD_REQUEST_DATA(
            HttpStatus.BAD_REQUEST,
            CommonErrorCode.E400001,
            CommonErrorCode.E400001.getErrorMessage()
    ),
    INVALID_ACCESSTOKEN(
            HttpStatus.UNAUTHORIZED,
            CommonErrorCode.E401001,
            CommonErrorCode.E401001.getErrorMessage()
    ),
    INVALID_REFRESHTOKEN(
            HttpStatus.UNAUTHORIZED,
            CommonErrorCode.E401003,
            CommonErrorCode.E401003.getErrorMessage()
    ),
    EXPIRED_REFRESHTOKEN(
            HttpStatus.UNAUTHORIZED,
            CommonErrorCode.E401004,
            CommonErrorCode.E401004.getErrorMessage()
    ),
    SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            CommonErrorCode.E500001,
            CommonErrorCode.E500001.getErrorMessage()
    );

    private final HttpStatus status;
    private final CommonErrorCode code;
    private final String message;

    CommonErrorType(HttpStatus status, CommonErrorCode code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
