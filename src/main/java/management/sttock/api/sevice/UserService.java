package management.sttock.api.sevice;

import management.sttock.api.request.user.SignupRequest;
import management.sttock.db.entity.User;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    void register(SignupRequest request);
    void validateNickname(String nickname);
    void validateEmail(String email);
    String findNickname(String email);

    User getUserInfo(HttpServletRequest request, Authentication authentication);
}
