package management.sttock.api.dto.message;

import lombok.Data;

@Data
public class SuccessMessage {
    private int code;
    private String message;
    public SuccessMessage(){
        code = 200;
        message = "등록 성공!";
    }
}
