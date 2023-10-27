package management.sttock.support.error;

import java.util.HashMap;
import java.util.LinkedHashMap;
import lombok.Getter;
import management.sttock.support.error.common.CommonErrorType;
import management.sttock.support.error.product.ProductErrorType;
import management.sttock.support.error.user.UserErrorType;

@Getter
public class ErrorResponse {
    private int status;
    private String code;
    private String message;
    HashMap<String, Object> response = new LinkedHashMap<>();
    public HashMap<String, Object> updateErrorResponse(CommonErrorType errorType) {
        this.status = errorType.getStatus().value();
        this.code = errorType.getCode().toString();
        this.message = errorType.getMessage();

        response.put("status", status);
        response.put("code", code);
        response.put("message", message);
        return response;
    }
    public HashMap<String, Object> updateErrorResponse(ProductErrorType errorType) {
        this.status = errorType.getStatus().value();
        this.code = errorType.getCode().toString();
        this.message = errorType.getMessage();

        response.put("status", status);
        response.put("code", code);
        response.put("message", message);
        return response;
    }
    public HashMap<String, Object> updateErrorResponse(UserErrorType errorType) {
        this.status = errorType.getStatus().value();
        this.code = errorType.getCode().toString();
        this.message = errorType.getMessage();

        response.put("status", status);
        response.put("code", code);
        response.put("message", message);
        return response;
    }
}
