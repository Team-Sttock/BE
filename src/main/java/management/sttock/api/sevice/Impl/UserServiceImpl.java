package management.sttock.api.sevice.Impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management.sttock.api.dto.user.PasswordRequest;
import management.sttock.api.dto.user.SignupRequest;
import management.sttock.api.dto.user.UserInfo;

import management.sttock.api.sevice.UserService;
import management.sttock.db.entity.User;
import management.sttock.db.repository.RefreshTokenRepository;
import management.sttock.db.repository.UserRepository;
import management.sttock.support.error.ApiException;
import management.sttock.support.error.ErrorType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MailSendServiceImpl mailSendService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void sendAuthNumber(String email) {
        validateEmail(email);
        mailSendService.sendAuthNumber(email);
    }

    @Override
    public void checkAuthNumber(String email, int authNumber) {
        validateEmail(email);
//        mailSendService.checkAuthNumber(email, authNumber);
    }

    @Override
    public void register(SignupRequest request) {
        validateloginId(request.getLoginId());
        validateEmail(request.getEmail());

//        mailSendService.checkVerificationStatus(request.getEmail());

        try {
            User user = User.builder()
                    .loginId(request.getLoginId())
                    .password(request.getPassword())
                    .name(request.getName())
                    .genderCd(request.getGenderCd())
                    .email(request.getEmail())
                    .familyNum(request.getFamilyNum())
                    .birthday(convertUtcToLocalDate(request.getBirthday()))
                    .build();

            userRepository.save(user);
        } catch (Exception e) {
            throw new ApiException(ErrorType.SERVER_ERROR);
        }
    }
    private static LocalDate convertUtcToLocalDate(String utcDateString){
        if (utcDateString == null || utcDateString.trim().isEmpty()) {
            return null;
        }
        ZonedDateTime utcDateTime = ZonedDateTime.parse(utcDateString)
                .withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime koreaDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        return koreaDateTime.toLocalDate();
    }

    @Override
    public String findloginId(String email) {
        try {
            return userRepository.findByEmail(email).get().getLoginId();
        } catch (NoSuchElementException e) {
            throw new ApiException(ErrorType.USER_NOT_FOUND);
        } catch (Exception e) {
            new ApiException(ErrorType.SERVER_ERROR);
        }
        return null;
    }
    @Override
    public void updateTempPassword(String email, String loginId) {
        boolean isNotFoundUser = !findloginId(email).equals(loginId);
        if (isNotFoundUser) {
            throw new ApiException(ErrorType.USER_NOT_FOUND);
        }
        String tempPassword = mailSendService.sendTempPassword(email);
        updatePassword(tempPassword, loginId);
    }

    @Override
    public void checkLoginId(String loginId) {
        validateloginId(loginId);
    }

    @Override
    public void validateloginId(String loginId) {
        boolean duplicateloginId = !userRepository.findByLoginId(loginId).isEmpty();
        if (loginId.isBlank()) {
            throw new ApiException(ErrorType.BAD_REQUEST_DATA);
        }
        if(duplicateloginId){
            throw new ApiException(ErrorType.LOGINID_CONFLICT);
        }
    }

    @Override
    public void validateEmail(String email) {
        boolean duplicateEmail = !userRepository.findByEmail(email).isEmpty();
        if(duplicateEmail){
            throw new ApiException(ErrorType.EMAIL_CONFLICT);
        }
    }

    @Override
    public UserInfo getUserInfo(HttpServletRequest request, Authentication authentication) {
        try {
            User user = userRepository.findByLoginId(authentication.getName()).get();

            String birthday = (user.getBirthday() == null) ? "" : user.getBirthday().toString();

            return new UserInfo(user.getLoginId(), user.getName(),
                    user.getGenderCd(), user.getEmail(), user.getFamilyNum(),
                    birthday);
        } catch (NoSuchElementException e) {
            throw new ApiException(ErrorType.USER_NOT_FOUND);
        } catch (Exception e) {
            throw new ApiException(ErrorType.SERVER_ERROR);
        }
    }

    @Override
    public void updateUserInfo(UserInfo requestDto, HttpServletRequest request, Authentication authentication) {
        boolean isUserEmpty = userRepository.findByLoginId(authentication.getName()).isEmpty();

        if(isUserEmpty) throw new ApiException(ErrorType.USER_NOT_FOUND);

        User user = userRepository.findByLoginId(authentication.getName()).get();
        boolean updateUserloginId = !user.getLoginId().equals(requestDto.getLoginId());
        boolean updateUserEmail = !user.getEmail().equals(requestDto.getEmail());

        if(updateUserloginId){
            validateloginId(requestDto.getLoginId());
        }
        if(updateUserEmail){
            validateEmail(requestDto.getEmail());
        }
        try {
            user.updateUser(requestDto, convertUtcToLocalDate(requestDto.getBirthday()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new ApiException(ErrorType.SERVER_ERROR);
        }
    }

    @Override
    public void updatePassword(PasswordRequest requestDto, HttpServletRequest request, Authentication authentication) {
        try {
            User user = userRepository.findByLoginId(authentication.getName())
                    .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

            if (!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {
                throw new ApiException(ErrorType.UNPROCESSABLE_PASSWORD);
            }
            updatePassword(requestDto.getNewPassword(), authentication.getName());
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(ErrorType.SERVER_ERROR);
        }
    }

    private void updatePassword(String password, String loginId) {
        User user = userRepository.findByLoginId(loginId).get();
        user.updatePassword(password);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void withdrawUser(HttpServletRequest request, Authentication authentication) {
        Optional<User> user = userRepository.findByLoginId(authentication.getName());
        boolean isAuthenticated = user.isEmpty();
        if(isAuthenticated){
            throw new ApiException(ErrorType.UNAUTHENTICATED_STATUS);
        }
        try {
            refreshTokenRepository.deleteAllByUser(user.get());
            userRepository.delete(user.get());
        } catch (Exception e) {
            throw new ApiException(ErrorType.SERVER_ERROR);
        }
    }

    @Override
    public void userMe(HttpServletRequest request, Authentication authentication) {
        try {
            userRepository.findByLoginId(authentication.getName());
        } catch (NoSuchElementException e) {
            throw new ApiException(ErrorType.USER_NOT_FOUND);
        } catch (Exception e) {
            throw new ApiException(ErrorType.SERVER_ERROR);
        }
    }

    @Override
    public int getNumberOfFamily(HttpServletRequest request, Authentication authentication) {

        try {
            return userRepository.findByLoginId(authentication.getName()).get().getFamilyNum();
        } catch (NoSuchElementException e) {
            throw new ApiException(ErrorType.USER_NOT_FOUND);
        } catch (Exception e) {
            throw new ApiException(ErrorType.SERVER_ERROR);
        }
    }
}