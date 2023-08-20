package management.sttock.api.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.api.request.user.SignupRequest;
import management.sttock.common.exception.ValidateException;

import management.sttock.db.entity.User;
import management.sttock.db.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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

        User user = User.builder()
                .nickname(request.getNickname())
                .password(request.getPassword())
                .name(request.getName())
                .genderCd(request.getGenderCd())
                .email(request.getEmail())
                .familyNum(request.getFamilyNum())
                .birthday(LocalDate.parse(request.getBirthday()))
                .build();

        userRepository.save(user);
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
}
