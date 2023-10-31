package management.sttock.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management.sttock.api.dto.auth.LoginRequest;
import management.sttock.api.dto.auth.CookieResponse;
import management.sttock.api.sevice.AuthServiceImpl;
import management.sttock.common.define.ApiPath;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPath.V1_AUTH)
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/auth/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest request, HttpServletResponse response){
        CookieResponse cookieResponse = authService.login(request);
        response.addCookie(cookieResponse.getAccessToken());
        response.addCookie(cookieResponse.getRefreshToken());
        return ResponseEntity.status(200).body(setResponseMesssage("message","로그인에 성공했습니다."));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request){
        authService.logout(request);
        return ResponseEntity.status(200).body(setResponseMesssage("message", "로그아웃에 성공했습니다."));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) {
        CookieResponse cookieResponse = authService.refreshToken(request);
        response.addCookie(cookieResponse.getAccessToken());
        response.addCookie(cookieResponse.getRefreshToken());

        return ResponseEntity.status(200).body(setResponseMesssage("message", "accessToken 갱신에 성공했습니다."));
    }

    private ConcurrentHashMap<String, Object> setResponseMesssage(String commentMessage, Object comment) {
        ConcurrentHashMap<String, Object> response = new ConcurrentHashMap<>();
        response.put(commentMessage, comment);
        return response;
    }
}