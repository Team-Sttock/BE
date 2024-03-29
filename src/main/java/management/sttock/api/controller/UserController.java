package management.sttock.api.controller;

import lombok.RequiredArgsConstructor;
import management.sttock.api.dto.user.PasswordRequest;
import management.sttock.api.dto.user.SignupRequest;
import management.sttock.api.dto.user.UserInfo;
import management.sttock.api.sevice.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;

    @GetMapping("/email")
    public ResponseEntity<Map<String, String>> sendAuthNumber(@Valid @Pattern(regexp = "^[a-zA-Z0-9]+([._%+-]*[a-zA-Z0-9])*@([a-zA-Z0-9]+\\.)+[a-zA-Z]{2,}$",
            message = "이메일을 입력해주세요") @RequestParam String email){
        userService.sendAuthNumber(email);
        return ResponseEntity.status(201).body(setResponseMesssage("message", "인증번호를 보냈습니다."));
    }
    @PostMapping("/email")
    public ResponseEntity<Map<String, String>> checkAuthNumber(@RequestParam String email, @RequestParam int authNumber){
        userService.checkAuthNumber(email, authNumber);
        return ResponseEntity.status(200).body(setResponseMesssage("message", "이메일 인증을 성공했습니다."));
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody SignupRequest request){
        request.changeEncodePassword(passwordEncoder.encode(request.getPassword()));
        userService.register(request);
        return ResponseEntity.status(200).body(setResponseMesssage("message","회원가입에 성공했습니다."));
    }

    @PostMapping("/user/nickname")
    public ResponseEntity<Map<String, String>> findNickname(@RequestParam String email){
        String nickname = userService.findNickname(email);
        return ResponseEntity.status(200).body(setResponseMesssage("nickname", nickname));
    }

    @PostMapping("/user/password/recover")
    public ResponseEntity<Map<String, String>> findPassword(@RequestParam String email,
                                               @RequestParam String nickname){
        userService.updateTempPassword(email, nickname);
        return ResponseEntity.status(200).body(setResponseMesssage("message","입력하신 이메일로 임시 비밀번호가 전송되었습니다"));
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfo> getUserInfo(HttpServletRequest request, Authentication authentication){
        UserInfo response = userService.getUserInfo(request, authentication);
        return ResponseEntity.status(200).body(response);
    }
    @PatchMapping("/user")
    public ResponseEntity<Map<String, String>> updateUserInfo(@Valid @RequestBody UserInfo requestDto, HttpServletRequest request,
                                                              Authentication authentication){
        userService.updateUserInfo(requestDto, request, authentication);
        return ResponseEntity.status(200).body(setResponseMesssage("message", "성공적으로 회원 정보를 수정했습니다."));
    }

    @PostMapping("/password")
    public ResponseEntity<Map<String, String>> updatePassword(@Valid @RequestBody PasswordRequest requestDto, HttpServletRequest request,
                                                              Authentication authentication){
        String password = passwordEncoder.encode(requestDto.getPassword());
        userService.updatePassword(password, request, authentication);
        return ResponseEntity.status(200).body(setResponseMesssage("message", "비밀번호를 성공적으로 변경했습니다."));
    }

    @DeleteMapping("/user")
    public ResponseEntity<Map<String, String>> withdrawUser(HttpServletRequest request, Authentication authentication){
        userService.withdrawUser(request, authentication);
        return ResponseEntity.status(200).body(setResponseMesssage("message", "회원 탈퇴하였습니다."));
    }

    @GetMapping("/me")
    public ResponseEntity userMe(HttpServletRequest request, Authentication authentication){
        userService.userMe(request, authentication);
        return ResponseEntity.ok().build();
    }

    private ConcurrentHashMap<String, String> setResponseMesssage(String commentMessage, String comment) {
        ConcurrentHashMap<String, String> response = new ConcurrentHashMap<>();
        response.put(commentMessage, comment);
        return response;
    }
}