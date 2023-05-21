package management.sttock.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SigninRequestDto {

    @NotBlank(message = "아이디를 입력헤 주세요")
    @Size(min = 3, max = 50)
    private String userId;
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 3, max = 100)
    private String password;

}
