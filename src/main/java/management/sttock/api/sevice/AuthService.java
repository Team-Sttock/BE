package management.sttock.api.sevice;

import management.sttock.api.request.user.LoginRequest;
import management.sttock.api.response.auth.CookieResponse;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    CookieResponse login(LoginRequest loginRequest);
    void logout(HttpServletRequest request);
    CookieResponse refreshToken(HttpServletRequest request);
}
