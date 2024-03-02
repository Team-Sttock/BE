package management.sttock.api.sevice;

import management.sttock.api.dto.auth.CookieResponse;
import management.sttock.api.dto.auth.LoginRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    CookieResponse login(LoginRequest loginRequest);
    void logout(HttpServletRequest request, HttpServletResponse response);
    CookieResponse refreshToken(HttpServletRequest request);
}
