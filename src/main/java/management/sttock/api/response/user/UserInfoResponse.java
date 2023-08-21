package management.sttock.api.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserInfoResponse {
    private String nickname;
    private String name;
    @JsonProperty("gender_cd")
    private int genderCd;
    private String email;
    @JsonProperty("family_num")
    private int familyNum;
    private String birthday;
}
