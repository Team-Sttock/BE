package management.sttock.api.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.api.request.user.SignupRequest;
import management.sttock.common.exception.ValidateException;

import management.sttock.db.entity.User;
import management.sttock.db.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Transactional
    @Override
    public void register(SignupRequest request) {
        validateNickname(request.getNickname());
        validateEmail(request.getEmail());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            User user = User.builder()
                    .nickname(request.getNickname())
                    .password(request.getPassword())
                    .name(request.getName())
                    .genderCd(request.getGenderCd())
                    .email(request.getEmail())
                    .familyNum(request.getFamilyNum())
                    .birthday(format.parse(request.getBirthday()))
                    .build();

            userRepository.save(user);
        } catch (Exception e) {
            new ValidateException(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입에 실패했습니다.");
        }
    }

    @Override
    public String findNickname(String email) {
        try {
            return userRepository.findByEmail(email).get().getNickname();
        } catch (NoSuchElementException e) {
            new ValidateException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        } catch (Exception e) {
            new ValidateException(HttpStatus.INTERNAL_SERVER_ERROR, "닉네임 찾기에 실패했습니다.");
        }
        return null;
    }

    @Override
    public void validateNickname(String nickname) {

        boolean duplicateNickname = !userRepository.findByNickname(nickname).isEmpty();
        if(duplicateNickname){
            throw new ValidateException(HttpStatus.CONFLICT, "이미 사용중인 닉네임입니다.");
        }
    }

    @Override
    public void validateEmail(String email) {
        boolean duplicateEmail = !userRepository.findByEmail(email).isEmpty();
        if(duplicateEmail){
            throw new ValidateException(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다.");
        }
    }

    @Override
    public User getUserInfo(HttpServletRequest request, Authentication authentication) {
        try {
            return userRepository.findByNickname(authentication.getName()).get();
        } catch (NoSuchElementException e) {
            new ValidateException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        } catch (Exception e) {
            new ValidateException(HttpStatus.INTERNAL_SERVER_ERROR, "닉네임 찾기에 실패했습니다.");
        }
        return null;
    }
}
