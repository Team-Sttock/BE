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
    private int phoneNumber;
    @NotBlank
    private String accessToken;
    @NotBlank
    private String tokenType;
    @NotBlank
    private int expiresIn;

    //entity-> dto
    public SigninResponseDto(Member member, String accessToken, String tokenType, int expiresIn) {
        this.userId = member.getUserId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.phoneNumber = member.getPhoneNumber();
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }
}
