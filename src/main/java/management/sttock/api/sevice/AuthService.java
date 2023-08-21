package management.sttock.api.sevice;

import management.sttock.api.request.user.LoginRequest;
import management.sttock.api.response.auth.CookieResponse;

public interface AuthService {
    CookieResponse login(LoginRequest loginRequest);
}
