package management.sttock.api.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import management.sttock.db.entity.RefreshToken;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRefreshRequestDto {

    private int userId;
    @NotBlank
    private String token;
    @NotBlank
    private RefreshToken refreshToken;

}
