package management.sttock.api.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequest {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
    @NotBlank
    @Size(min = 6)
    private String password;
    @NotBlank
    private String name;
    @JsonProperty("gender_cd")
    private int genderCd;
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @JsonProperty("family_num")
    private int familyNum;
    private String birthday;

    public void changeEncodePassword(String encodedPassword){
        this.password = encodedPassword;
    }
}
