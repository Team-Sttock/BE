package management.sttock.support.error.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum UserErrorType {
    LOGIN_FAILED(
            HttpStatus.UNAUTHORIZED,
            UserErrorCode.E401002,
            UserErrorCode.E401002.getErrorMessage()
    ),
    FORBIDDEN(
            HttpStatus.FORBIDDEN,
            UserErrorCode.E403001,
            UserErrorCode.E403001.getErrorMessage()
    ),
    USER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            UserErrorCode.E404001,
            UserErrorCode.E404001.getErrorMessage()
    ),
    LOGINID_CONFLICT(
            HttpStatus.CONFLICT,
            UserErrorCode.E409001,
            UserErrorCode.E409001.getErrorMessage()
    ),
    EMAIL_CONFLICT(
            HttpStatus.CONFLICT,
            UserErrorCode.E409002,
            UserErrorCode.E409002.getErrorMessage()
    );
    private final HttpStatus status;
    private final UserErrorCode code;
    private final String message;

    UserErrorType(HttpStatus status, UserErrorCode code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
