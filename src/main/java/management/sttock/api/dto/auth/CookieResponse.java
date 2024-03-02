package management.sttock.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseCookie;

@Getter
@AllArgsConstructor
public class CookieResponse {
    private ResponseCookie accessToken;
    private ResponseCookie refreshToken;
}
