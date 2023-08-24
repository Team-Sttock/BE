package management.sttock.api.controller;

import lombok.RequiredArgsConstructor;
import management.sttock.api.request.user.SignupRequest;
import management.sttock.api.response.user.UserInfoResponse;
import management.sttock.api.sevice.UserServiceImpl;
import management.sttock.db.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody SignupRequest request){
        request.changeEncodePassword(passwordEncoder.encode(request.getPassword()));
        //이메일로 회원 인증 로직 추가
        userService.register(request);
        return ResponseEntity.status(200).body(setResponseMesssage("message","회원가입에 성공했습니다."));
    }

    @PostMapping("/find-nickname")
    public ResponseEntity<Map<String, String>> findNickname(@RequestParam String email){
        String nickname = userService.findNickname(email);
        return ResponseEntity.status(200).body(setResponseMesssage("nickname", nickname));
    }

    @PostMapping("/find-password")
    public ResponseEntity<Map<String, String>> findPassword(@RequestParam String email,
                                               @RequestParam String nickname){
        /**
         * 이메일로 임시 비밀번호 전송 로직 추가
         */
        return ResponseEntity.status(200).body(setResponseMesssage("message","입력하신 이메일로 임시 비밀번호가 전송되었습니다"));
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfoResponse> getUserInfo(HttpServletRequest request, Authentication authentication){
        User user = userService.getUserInfo(request, authentication);

        UserInfoResponse response = new UserInfoResponse(user.getNickname(), user.getName(),
                user.getGenderCd(), user.getEmail(), user.getFamilyNum(), user.getBirthday().toString());

        return ResponseEntity.status(200).body(response);
    }

    private ConcurrentHashMap<String, String> setResponseMesssage(String commentMessage, String comment) {
        ConcurrentHashMap<String, String> response = new ConcurrentHashMap<>();
        response.put(commentMessage, comment);
        return response;
    }

}
