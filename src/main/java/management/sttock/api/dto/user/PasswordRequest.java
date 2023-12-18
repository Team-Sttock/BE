package management.sttock.api.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PasswordRequest {

    private String oldPassword;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{6,}$",
            message = "비밀번호는 영어, 숫자, 특수문자를 포함하여 6자리 이상이어야 합니다.")
    private String newPassword;

    public void changeEncodeNewPassword(String encodedNewPassword) {
        this.newPassword = encodedNewPassword;
    }
}
