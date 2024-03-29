package management.sttock.api.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.api.dto.user.SignupRequest;
import management.sttock.api.dto.user.UserInfo;
import management.sttock.common.exception.ValidateException;

import management.sttock.db.entity.User;
import management.sttock.db.repository.RefreshTokenRepository;
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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MailSendServiceImpl mailSendService;

    @Override
    public void sendAuthNumber(String email) {
        mailSendService.sendAuthNumber(email);
    }

    @Override
    public void checkAuthNumber(String email, int authNumber) {
        mailSendService.checkAuthNumber(email, authNumber);
    }

    @Override
    public void register(SignupRequest request) {
        validateNickname(request.getNickname());
        validateEmail(request.getEmail());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        mailSendService.checkVerificationStatus(request.getEmail());

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
    public void updateTempPassword(String email, String nickname) {
        boolean isNotFoundUser = !findNickname(email).equals(nickname);
        if (isNotFoundUser) {
            throw new ValidateException(HttpStatus.NOT_FOUND, "일치하는 회원이 없습니다.");
        }
        String tempPassword = mailSendService.sendTempPassword(email);
        updatePassword(tempPassword, nickname);
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
    public UserInfo getUserInfo(HttpServletRequest request, Authentication authentication) {
        try {
            User user = userRepository.findByNickname(authentication.getName()).get();
            return new UserInfo(user.getNickname(), user.getName(),
                    user.getGenderCd(), user.getEmail(), user.getFamilyNum(), user.getBirthday().toString());
        } catch (NoSuchElementException e) {
            new ValidateException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        } catch (Exception e) {
            new ValidateException(HttpStatus.INTERNAL_SERVER_ERROR, "닉네임 찾기에 실패했습니다.");
        }
        return null;
    }

    @Override
    public void updateUserInfo(UserInfo requestDto, HttpServletRequest request, Authentication authentication) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        boolean isUserEmpty = userRepository.findByNickname(authentication.getName()).isEmpty();

        if(isUserEmpty) throw new ValidateException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");

        User user = userRepository.findByNickname(authentication.getName()).get();
        boolean updateUserNickname = !user.getNickname().equals(requestDto.getNickname());
        boolean updateUserEmail = !user.getEmail().equals(requestDto.getEmail());

        if(updateUserNickname){
            validateNickname(requestDto.getNickname());
        }
        if(updateUserEmail){
            validateEmail(requestDto.getEmail());
        }
        try {
            user.updateUser(requestDto, format.parse(requestDto.getBirthday()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new ValidateException(HttpStatus.INTERNAL_SERVER_ERROR, "회원 정보 수정에 실패했습니다.");
        }
    }

    @Override
    public void updatePassword(String password, HttpServletRequest request, Authentication authentication) {
        try {
            updatePassword(password, authentication.getName());
        } catch (NoSuchElementException e) {
            new ValidateException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        } catch (Exception e) {
            new ValidateException(HttpStatus.INTERNAL_SERVER_ERROR, "비밀번호 변경에 실패했습니다.");
        }
    }

    private void updatePassword(String password, String nickname) {
        User user = userRepository.findByNickname(nickname).get();
        user.updatePassword(password);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void withdrawUser(HttpServletRequest request, Authentication authentication) {
        Optional<User> user = userRepository.findByNickname(authentication.getName());
        boolean isAuthenticated = user.isEmpty();
        if(isAuthenticated){
            throw new ValidateException(HttpStatus.UNAUTHORIZED, "로그인 후 이용가능 합니다.");
        }
        try {
            refreshTokenRepository.deleteAllByUser(user.get());
            userRepository.delete(user.get());
        } catch (Exception e) {
            throw new ValidateException(HttpStatus.INTERNAL_SERVER_ERROR, "회원 탈퇴에 실패했습니다.");
        }
    }

    @Override
    public void userMe(HttpServletRequest request, Authentication authentication) {
        try {
            userRepository.findByNickname(authentication.getName());
        } catch (NoSuchElementException e) {
            new ValidateException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        } catch (Exception e) {
            new ValidateException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        }
    }
}
