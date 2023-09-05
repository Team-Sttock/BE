package management.sttock.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class UserInfo {
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "아이디는 영어와 숫자로만 구성되어야 합니다.")
    @JsonProperty("login_id")
    private String loginId;
    @NotBlank
    private String name;
    @JsonProperty("gender_cd")
    private int genderCd;
    @Pattern(regexp = "^[a-zA-Z0-9]+([._%+-]*[a-zA-Z0-9])*@([a-zA-Z0-9]+\\.)+[a-zA-Z]{2,}$", message = "이메일을 입력해주세요")
    private String email;
    @JsonProperty("family_num")
    private int familyNum;
    private String birthday;
}
