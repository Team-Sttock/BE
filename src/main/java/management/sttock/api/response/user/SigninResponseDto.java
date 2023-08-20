package management.sttock.api.response.user;

import lombok.*;
import management.sttock.db.entity.Member;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninResponseDto {
    private String name;
    @NotBlank
    private String token;

    //entity-> dto
    public SigninResponseDto(Member member, String token) {
        this.name = member.getName();
        this.token = token;
    }
}
