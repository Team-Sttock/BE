package management.sttock.api.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateUserInfoRequest {
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
    @NotBlank
    private String name;
    @JsonProperty("gender_cd")
    private int genderCd;
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @JsonProperty("family_num")
    private int familyNum;
    private String birthday;
}
