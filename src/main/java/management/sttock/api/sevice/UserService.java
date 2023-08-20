package management.sttock.api.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.db.entity.User;
import management.sttock.db.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(User user) {
        validationDupliateMember(user);//중복 회원 검증

        user.encodePassword(passwordEncoder);
        userRepository.save(user);

        return user.getId();
    }

    //아이디, 이메일 중복 검사
    private void validationDupliateMember(User user) {
        List<User> findSameId = userRepository.findByUserIdForList(user.getNickname());
        //List<User> findSameEmail = userRepository.findByEmail(user.getEmail());
        if (!findSameId.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
//        else if (!findSameEmail.isEmpty()) {
//            throw new IllegalStateException("이미 존재하는 이메일입니다.");
//        }
    }

    /**
     * 조회
     */
    //회원 조회
    public List<User> findUsers() {
        return userRepository.findAll();
    }
    //회원 pk로 조회
    public User findUser(Long id) {
        return userRepository.findOne(id);
    }

//    //회원아이디로 조회
//    public User findUserid(String userId) {
//        return userRepository.findOneByUserId(userId);
//    }

    public User findUseridforLogin(String userId) {
        return userRepository.findOneByUserIdForLong(userId);
    }

//    //이메일로 회원아이디 찾기
//    public String findEmail(String email) {
//        return userRepository.findOneByEmail(email).getUserId();
//    }
//
//    //회원 삭제
//    @Transactional
//    public String deleteUser(String userId) {
//        User findUser = userRepository.findOneByUserId(userId);
//        Optional<User> userOptional = Optional.ofNullable(findUser);
//
//        if (userOptional.isPresent()) {
//            String findId = findUser.get();
//            int deleteCount = userRepository.delete(findId);
//            return "delete success";
//        } else {
//            throw new IllegalStateException("존재하지 않는 회원입니다.");
//        }
    //}

}
