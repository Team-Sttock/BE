package management.sttock.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequest {

    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "아이디는 영어와 숫자로만 구성되어야 합니다.")
    @JsonProperty("login_id")
    private String loginId;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{6,}$",
            message = "비밀번호는 영어, 숫자, 특수문자를 포함하여 6자리 이상이어야 합니다.")
    private String password;
    @NotBlank
    private String name;
    @JsonProperty("gender_cd")
    private int genderCd;
    @Pattern(regexp = "^[a-zA-Z0-9]+([._%+-]*[a-zA-Z0-9])*@([a-zA-Z0-9]+\\.)+[a-zA-Z]{2,}$", message = "이메일을 입력해주세요")
    private String email;
    @JsonProperty("family_num")
    private int familyNum;
    private String birthday;

    public void changeEncodePassword(String encodedPassword){
        this.password = encodedPassword;
    }
}
