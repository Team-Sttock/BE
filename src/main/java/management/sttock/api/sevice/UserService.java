package management.sttock.api.sevice;

import management.sttock.api.dto.user.SignupRequest;
import management.sttock.api.dto.user.UserInfo;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    void sendAuthNumber(String email);
    void checkAuthNumber(String email, int authNumber);
    void register(SignupRequest request);
    void validateloginId(String loginId);
    void validateEmail(String email);
    String findloginId(String email);
    UserInfo getUserInfo(HttpServletRequest request, Authentication authentication);
    void updateUserInfo(UserInfo requestDto, HttpServletRequest request, Authentication authentication);
    void withdrawUser(HttpServletRequest request, Authentication authentication);
    void updatePassword(String password, HttpServletRequest request, Authentication authentication);
    void userMe(HttpServletRequest request, Authentication authentication);
    void updateTempPassword(String email, String loginId);
}
