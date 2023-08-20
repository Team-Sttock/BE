package management.sttock.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management.sttock.api.request.auth.TokenRefreshRequestDto;
import management.sttock.api.response.auth.TokenRefreshResponseDto;
import management.sttock.common.auth.local.TokenProvider;
import management.sttock.db.entity.RefreshToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenProvider tokenProvider;

    // accessToken 만료시, accessToken과 refreshToken로 accessToken 발급받기 로직
    @PostMapping("/refreshToken")
    public ResponseEntity<TokenRefreshResponseDto> refreshToken(@Valid @RequestBody TokenRefreshRequestDto request) {

        RefreshToken refreshToken = request.getRefreshToken();
        tokenProvider.valicateForReIssue(refreshToken);

        String newToken = tokenProvider.generateNewToken();
        return ResponseEntity.status(HttpStatus.OK).body(new TokenRefreshResponseDto(newToken, refreshToken));
    }
}
