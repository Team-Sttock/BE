package management.sttock.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management.sttock.api.request.auth.TokenRefreshRequestDto;
import management.sttock.api.request.user.LoginRequest;
import management.sttock.api.response.auth.CookieResponse;
import management.sttock.api.response.auth.TokenRefreshResponseDto;
import management.sttock.api.sevice.AuthServiceImpl;
import management.sttock.common.auth.local.TokenProvider;
import management.sttock.db.entity.RefreshToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final TokenProvider tokenProvider;

    // accessToken 만료시, accessToken과 refreshToken로 accessToken 발급받기 로직
    @PostMapping("/refreshToken")
    public ResponseEntity<TokenRefreshResponseDto> refreshToken(@Valid @RequestBody TokenRefreshRequestDto request) {

        RefreshToken refreshToken = request.getRefreshToken();
        tokenProvider.valicateForReIssue(refreshToken); //이거 authservice로 리팩토링하기

        String newToken = tokenProvider.generateNewToken();
        return ResponseEntity.status(HttpStatus.OK).body(new TokenRefreshResponseDto(newToken, refreshToken));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response){
        CookieResponse cookieResponse = authService.login(request);
        response.addCookie(cookieResponse.getAccessToken());
        response.addCookie(cookieResponse.getRefreshToken());

        return ResponseEntity.status(200).body(setResponseMesssage("message","로그인에 성공했습니다."));
    }

    private ConcurrentHashMap<String, String> setResponseMesssage(String commentMessage, String comment) {
        ConcurrentHashMap<String, String> response = new ConcurrentHashMap<>();
        response.put(commentMessage, comment);
        return response;
    }
}
