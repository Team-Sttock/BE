package management.sttock.support.error.user;

import lombok.Getter;

@Getter
public enum UserErrorCode {

    E401002("LOGIN_FAILED"),
    E403001("FORBIDDEN"),
    E404001("USER_NOT_FOUND"),
    E409001("LOGINID_CONFLICT"),
    E409002("EMAIL_CONFLICT");

    private String errorMessage;

    UserErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
