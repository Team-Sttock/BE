package management.sttock.support.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    E400001("BAD_REQUEST_DATA"),
    E401001("INVALID_ACCESSTOKEN"),
    E401002("LOGIN_FAILED"),
    E401003("INVALID_REFRESHTOKEN"),
    E401004("EXPIRED_REFRESHTOKEN"),
    E401005("UNAUTHENTICATED_STATUS"),
    E403001("FORBIDDEN"),
    E404001("USER_NOT_FOUND"),
    E404002("PRODUCT_NOT_FOUND"),
    E404003("NO_OFFERED_PRODUCT"),
    E404004("WEEKLY_NOT_FOUND"),
    E409001("LOGINID_CONFLICT"),
    E409002("EMAIL_CONFLICT"),
    E500001("SERVER_ERROR");

    private String errorMessage;

    ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
