package management.sttock.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management.sttock.api.request.user.LoginRequest;
import management.sttock.api.response.auth.CookieResponse;
import management.sttock.api.sevice.AuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response){
        CookieResponse cookieResponse = authService.login(request);
        response.addCookie(cookieResponse.getAccessToken());
        response.addCookie(cookieResponse.getRefreshToken());
        return ResponseEntity.status(200).body(setResponseMesssage("message","로그인에 성공했습니다."));
    }

    @RequestMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request){
        authService.logout(request);
        return ResponseEntity.status(200).body(setResponseMesssage("message", "로그아웃에 성공했습니다."));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        CookieResponse cookieResponse = authService.refreshToken(request);
        response.addCookie(cookieResponse.getAccessToken());
        response.addCookie(cookieResponse.getRefreshToken());

        return ResponseEntity.status(200).body(setResponseMesssage("message", "accessToken 갱신에 성공했습니다."));
    }

    private ConcurrentHashMap<String, String> setResponseMesssage(String commentMessage, String comment) {
        ConcurrentHashMap<String, String> response = new ConcurrentHashMap<>();
        response.put(commentMessage, comment);
        return response;
    }
}