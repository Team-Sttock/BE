package management.sttock.api.request.user;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SigninRequestDto {

    @NotBlank(message = "닉네임을 입력해 주세요")
    @Size(min = 3, max = 50)
    private String nickName;
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 3, max = 100)
    private String password;

}
