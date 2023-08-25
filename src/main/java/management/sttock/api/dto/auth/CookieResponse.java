package management.sttock.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.Cookie;

@Getter
@AllArgsConstructor
public class CookieResponse {
    private Cookie accessToken;
    private Cookie refreshToken;
}
