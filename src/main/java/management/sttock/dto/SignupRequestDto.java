package management.sttock.dto;

import lombok.*;
import management.sttock.domain.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 3, max = 50)
    private String userId;
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 3, max = 100)
    private String password;
    @NotBlank(message = "이름을 입력해주세요")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9_-]{3,20}$")
    private String name;
    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    private String email;
    @NotBlank(message = "전화번호를 입력해주세요")
    private int phoneNumber;

    @Builder
    public SignupRequestDto(String userId, String password, String name, String email, int phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    //dto-> entity
    public Member toEntity() {
        return Member.builder()
                .userId(this.userId)
                .userPassword(this.password)
                .name(this.name)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .build();
    }

}
