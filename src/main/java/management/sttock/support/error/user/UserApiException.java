package management.sttock.support.error.user;

import lombok.Getter;

@Getter
public class UserApiException extends RuntimeException {

    private UserErrorType errorType;

    public UserApiException(UserErrorType errorType) {
        this.errorType = errorType;
    }
}
