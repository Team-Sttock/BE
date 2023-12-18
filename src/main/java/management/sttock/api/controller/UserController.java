package management.sttock.api.controller;

import lombok.RequiredArgsConstructor;
import management.sttock.api.dto.user.PasswordRequest;
import management.sttock.api.dto.user.SignupRequest;
import management.sttock.api.dto.user.UserInfo;
import management.sttock.api.sevice.Impl.UserServiceImpl;
import management.sttock.common.define.ApiPath;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(ApiPath.V1_USER)
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;

    @PostMapping("/email/verification-code")
    public ResponseEntity sendAuthNumber(@Valid @Pattern(regexp = "^[a-zA-Z0-9]+([._%+-]*[a-zA-Z0-9])*@([a-zA-Z0-9]+\\.)+[a-zA-Z]{2,}$",
            message = "이메일을 입력해주세요") @RequestParam String email){
        userService.sendAuthNumber(email);
        return ResponseEntity.status(201).body(setResponseMesssage("message", "입력하신 이메일로 인증번호가 전송되었습니다."));
    }
    @PostMapping("/email/check-verification-code")
    public ResponseEntity checkAuthNumber(@RequestParam String email, @RequestParam("auth_number") int authNumber){
        userService.checkAuthNumber(email, authNumber);
        return ResponseEntity.status(200).body(setResponseMesssage("message", "이메일 인증을 성공했습니다."));
    }

    @PostMapping(ApiPath.SIGN_UP)
    public ResponseEntity signup(@Valid @RequestBody SignupRequest request){
        request.changeEncodePassword(passwordEncoder.encode(request.getPassword()));
        userService.register(request);
        return ResponseEntity.status(200).body(setResponseMesssage("message","회원가입에 성공했습니다."));
    }
    @PostMapping("/check")
    public ResponseEntity checkLoginId(@RequestParam("login_id") String loginId){
        userService.checkLoginId(loginId);
        return ResponseEntity.status(200).body(setResponseMesssage("message", "사용 가능한 아이디 입니다."));
    }

    @PostMapping("/loginId")
    public ResponseEntity findloginId(@RequestParam String email){
        String loginId = userService.findloginId(email);
        return ResponseEntity.status(200).body(setResponseMesssage("loginId", loginId));
    }

    @PostMapping("/temp-password")
    public ResponseEntity findPassword(@RequestParam String email,
                                               @RequestParam("login_id") String loginId){
        userService.updateTempPassword(email, loginId);
        return ResponseEntity.status(200).body(setResponseMesssage("message","입력하신 이메일로 임시 비밀번호가 전송되었습니다"));
    }

    @GetMapping
    public ResponseEntity getUserInfo(HttpServletRequest request, Authentication authentication){
        UserInfo response = userService.getUserInfo(request, authentication);
        return ResponseEntity.status(200).body(response);
    }
    @PatchMapping
    public ResponseEntity updateUserInfo(@Valid @RequestBody UserInfo requestDto, HttpServletRequest request,
                                                              Authentication authentication){
        userService.updateUserInfo(requestDto, request, authentication);
        return ResponseEntity.status(200).body(setResponseMesssage("message", "성공적으로 회원 정보를 수정했습니다."));
    }

    @PatchMapping("/password")
    public ResponseEntity updatePassword(@Valid @RequestBody PasswordRequest requestDto, HttpServletRequest request,
                                                              Authentication authentication){
        requestDto.changeEncodeNewPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userService.updatePassword(requestDto, request, authentication);
        return ResponseEntity.status(200).body(setResponseMesssage("message", "비밀번호를 성공적으로 변경했습니다."));
    }

    @DeleteMapping
    public ResponseEntity withdrawUser(HttpServletRequest request, Authentication authentication){
        userService.withdrawUser(request, authentication);
        return ResponseEntity.status(200).body(setResponseMesssage("message", "회원 탈퇴하였습니다."));
    }

    @GetMapping("/me")
    public ResponseEntity userMe(HttpServletRequest request, Authentication authentication){
        userService.userMe(request, authentication);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/family_number")
    public ResponseEntity getNumberOfFamily(HttpServletRequest request, Authentication authentication){
        int numberOfFamily = userService.getNumberOfFamily(request, authentication);
        return ResponseEntity.status(200).body(setResponseMesssage("family_number", numberOfFamily));
    }

    private ConcurrentHashMap<String, Object> setResponseMesssage(String commentMessage, Object comment) {
        ConcurrentHashMap<String, Object> response = new ConcurrentHashMap<>();
        response.put(commentMessage, comment);
        return response;
    }
}