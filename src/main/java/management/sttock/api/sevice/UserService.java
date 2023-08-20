package management.sttock.api.sevice;

import management.sttock.api.request.user.SignupRequest;

public interface UserService {

    void register(SignupRequest request);
    void validateNickname(String nickname);
    void validateEmail(String email);

}
