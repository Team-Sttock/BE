package management.sttock.support.error.common;

import lombok.Getter;

@Getter
public enum CommonErrorCode {
    E400001("BAD_REQUEST_DATA"),
    E401001("INVALID_ACCESSTOKEN"),
    E401003("INVALID_REFRESHTOKEN"),
    E401004("EXPIRED_REFRESHTOKEN"),
    E500001("SERVER_ERROR");

    private String errorMessage;

    CommonErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
