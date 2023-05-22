package management.sttock.dto;

import lombok.*;
import management.sttock.domain.Member;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninResponseDto {
    private String userId;
    private String email;
    private String name;
    private String phoneNumber;
    @NotBlank
    private String token;

    //entity-> dto
    public SigninResponseDto(Member member, String token) {
        this.userId = member.getUserId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.phoneNumber = member.getPhoneNumber();
        this.token = token;
    }
}
