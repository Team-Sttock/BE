package management.sttock.support.error.product;

import lombok.Getter;

@Getter
public enum ProductErrorCode {

    E404002("PRODUCT_NOT_FOUND"),

    E404003("NO_OFFERED_PRODUCT"),
    E404004("WEEKLY_NOT_FOUND");
    private String errorMessage;

    ProductErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
