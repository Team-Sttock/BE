package management.sttock.api.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequest {

    @NotBlank(message = "잘못된 닉네임입니다.")
    private String nickname;
    @NotBlank
    @Size(min = 6)
    private String password;
    @NotBlank
    private String name;
    @JsonProperty("gender_cd")
    private int genderCd;
    @NotBlank(message = "잘못된 이메일입니다.")
    private String email;
    @JsonProperty("family_num")
    private int familyNum;
    private String birthday;

    public void changeEncodePassword(String encodedPassword){
        this.password = encodedPassword;
    }
}
