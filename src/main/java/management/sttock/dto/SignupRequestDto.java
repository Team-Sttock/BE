package management.sttock.dto;

import lombok.*;
import management.sttock.domain.Member;

import javax.validation.constraints.*;


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
    private String name;
    @NotNull(message = "이메일을 입력해주세요")
    private String email;

    @NotNull
    private String phoneNumber;

    @Builder
    public SignupRequestDto(String userId, String password, String name, String email, String phoneNumber) {
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
