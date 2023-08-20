package management.sttock.api.response.user;

import lombok.*;
import management.sttock.db.entity.User;
import management.sttock.db.entity.RefreshToken;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninResponseDto {
    private String name;
    @NotBlank
    private String token;

    @NotBlank
    private RefreshToken refreshToken;

    //entity-> dto
    public SigninResponseDto(User user, String token, RefreshToken refreshToken) {
        this.name = user.getName();
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
