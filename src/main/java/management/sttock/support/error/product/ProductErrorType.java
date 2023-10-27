package management.sttock.support.error.product;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ProductErrorType {
    PRODUCT_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            ProductErrorCode.E404002,
            ProductErrorCode.E404002.getErrorMessage()
    ),
    NO_OFFERED_PRODUCT(
            HttpStatus.NOT_FOUND,
            ProductErrorCode.E404003,
            ProductErrorCode.E404003.getErrorMessage()
    ),
    WEEKLY_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            ProductErrorCode.E404004,
            ProductErrorCode.E404004.getErrorMessage()
    );
    private final HttpStatus status;
    private final ProductErrorCode code;
    private final String message;

    ProductErrorType(HttpStatus status, ProductErrorCode code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
