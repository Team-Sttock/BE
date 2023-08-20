package management.sttock.api.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import management.sttock.db.entity.RefreshToken;

@Getter
@AllArgsConstructor
public class TokenRefreshResponseDto {
    private String token;
    private RefreshToken refreshToken;
}
