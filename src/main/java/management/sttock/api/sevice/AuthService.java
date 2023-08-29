package management.sttock.api.sevice;

import management.sttock.api.dto.auth.LoginRequest;
import management.sttock.api.dto.auth.CookieResponse;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    CookieResponse login(LoginRequest loginRequest);
    void logout(HttpServletRequest request);
    CookieResponse refreshToken(HttpServletRequest request);
}
