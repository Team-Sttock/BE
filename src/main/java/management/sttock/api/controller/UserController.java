package management.sttock.api.controller;

import lombok.RequiredArgsConstructor;
import management.sttock.api.request.user.SignupRequest;
import management.sttock.api.sevice.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request){
        request.changeEncodePassword(passwordEncoder.encode(request.getPassword()));
        userService.register(request);
        return ResponseEntity.status(200).body("회원가입에 성공했습니다.");
    }
}
