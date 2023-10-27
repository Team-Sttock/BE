package management.sttock.support.error;

import java.util.HashMap;
import java.util.LinkedHashMap;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private int status;
    private String code;
    private String message;
    HashMap<String, Object> response = new LinkedHashMap<>();
    public HashMap<String, Object> updateErrorResponse(ErrorType errorType) {
        this.status = errorType.getStatus().value();
        this.code = errorType.getCode().toString();
        this.message = errorType.getMessage();

        response.put("status", status);
        response.put("code", code);
        response.put("message", message);
        return response;
    }
}
