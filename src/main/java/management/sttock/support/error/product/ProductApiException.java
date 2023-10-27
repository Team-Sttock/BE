package management.sttock.support.error.product;

import lombok.Getter;

@Getter
public class ProductApiException extends RuntimeException {
    private ProductErrorType errorType;

    public ProductApiException(ProductErrorType errorType) {
        this.errorType = errorType;
    }
}
