package management.sttock.api.sevice;

import management.sttock.api.dto.user.SignupRequest;
import management.sttock.api.dto.user.UserInfo;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    void register(SignupRequest request);
    void validateNickname(String nickname);
    void validateEmail(String email);
    String findNickname(String email);

    UserInfo getUserInfo(HttpServletRequest request, Authentication authentication);

    void updateUserInfo(UserInfo requestDto, HttpServletRequest request, Authentication authentication);
}
