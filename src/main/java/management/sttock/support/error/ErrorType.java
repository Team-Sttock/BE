package management.sttock.support.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorType {
    BAD_REQUEST_DATA(
            HttpStatus.BAD_REQUEST,
            ErrorCode.E400001,
            ErrorCode.E400001.getErrorMessage()
    ),
    INVALID_ACCESSTOKEN(
            HttpStatus.UNAUTHORIZED,
            ErrorCode.E401001,
            ErrorCode.E401001.getErrorMessage()
    ),
    LOGIN_FAILED(
            HttpStatus.UNAUTHORIZED,
            ErrorCode.E401002,
            ErrorCode.E401002.getErrorMessage()
    ),
    INVALID_REFRESHTOKEN(
            HttpStatus.UNAUTHORIZED,
            ErrorCode.E401003,
            ErrorCode.E401003.getErrorMessage()
    ),
    EXPIRED_REFRESHTOKEN(
            HttpStatus.UNAUTHORIZED,
            ErrorCode.E401004,
            ErrorCode.E401004.getErrorMessage()
    ),
    UNAUTHENTICATED_STATUS(
      HttpStatus.UNAUTHORIZED,
      ErrorCode.E401005,
      ErrorCode.E401005.getErrorMessage()
    ),
    FORBIDDEN(
            HttpStatus.FORBIDDEN,
            ErrorCode.E403001,
            ErrorCode.E403001.getErrorMessage()
    ),
    USER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            ErrorCode.E404001,
            ErrorCode.E404001.getErrorMessage()
    ),
    PRODUCT_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            ErrorCode.E404002,
            ErrorCode.E404002.getErrorMessage()
    ),
    NO_OFFERED_PRODUCT(
            HttpStatus.NOT_FOUND,
            ErrorCode.E404003,
            ErrorCode.E404003.getErrorMessage()
    ),
    WEEKLY_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            ErrorCode.E404004,
            ErrorCode.E404004.getErrorMessage()
    ),
    LOGINID_CONFLICT(
            HttpStatus.CONFLICT,
            ErrorCode.E409001,
            ErrorCode.E409001.getErrorMessage()
    ),
    EMAIL_CONFLICT(
            HttpStatus.CONFLICT,
            ErrorCode.E409002,
            ErrorCode.E409002.getErrorMessage()
    ),
    SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorCode.E500001,
            ErrorCode.E500001.getErrorMessage()
    );

    private final HttpStatus status;
    private final ErrorCode code;
    private final String message;

    ErrorType(HttpStatus status, ErrorCode code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
