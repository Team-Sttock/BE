package management.sttock.api.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.api.dto.user.LoginRequest;
import management.sttock.api.dto.auth.CookieResponse;
import management.sttock.common.auth.local.CustomUserDetailsService;
import management.sttock.common.auth.local.TokenProvider;
import management.sttock.common.exception.ValidateException;
import management.sttock.db.entity.RefreshToken;
import management.sttock.db.entity.User;
import management.sttock.db.repository.AuthRespository;
import management.sttock.db.repository.RefreshTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRespository authRespository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    @Override
    @Transactional
    public CookieResponse login(LoginRequest request) {
        try {

            Optional<User> user = authRespository.findByNickname(request.getNickname());
            boolean isNotMatchPassword = !passwordEncoder.matches(request.getPassword(), user.get().getPassword());

            if (isNotMatchPassword) {
                throw new ValidateException(HttpStatus.UNAUTHORIZED, "비밀번호를 잘못 입력했습니다.");
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getNickname());
            String token = tokenProvider.createToken(userDetails);
            RefreshToken refreshToken = tokenProvider.createRefreshToken(user.get(), userDetails);

            refreshTokenRepository.save(refreshToken);
            Cookie accessTokenInCookie = setTokenInCookie("accessToken", token);
            Cookie refreshTokenInCookie = setTokenInCookie("refreshToken", refreshToken.getToken());
            return new CookieResponse(accessTokenInCookie, refreshTokenInCookie);
        } catch (NoSuchElementException e) {
            throw new ValidateException(HttpStatus.UNAUTHORIZED, "등록되지 않은 아이디이거나, 아이디를 잘못 입력했습니다.");
        }
    }

    @Transactional
    @Override
    public void logout(HttpServletRequest request) {
        try {
            RefreshToken refreshToken = getRefreshToken(request);
            refreshTokenRepository.delete(refreshToken);
        } catch (Exception e) {
            throw new ValidateException(HttpStatus.INTERNAL_SERVER_ERROR, "로그아웃에 실패했습니다. 다시 시도해 주세요.");
        }
    }

    @Transactional
    @Override
    public CookieResponse refreshToken(HttpServletRequest request) {
        RefreshToken refreshToken = getRefreshToken(request);
        String renewToken = tokenProvider.renewToken(refreshToken);

        Cookie accessTokenInCookie = setTokenInCookie("accessToken", renewToken);
        Cookie refreshTokenInCookie = setTokenInCookie("refreshToken", refreshToken.getToken());

        return new CookieResponse(accessTokenInCookie, refreshTokenInCookie);
    }

    private RefreshToken getRefreshToken(HttpServletRequest request) {
        String token = getRefreshTokenInCookie(request);
        List<RefreshToken> refreshTokens = refreshTokenRepository.findByTokenOrderByExpiredDtDesc(token);
        if(!refreshTokens.isEmpty()) {
            return refreshTokens.get(0);
        }
        return null;
    }

    private static String getRefreshTokenInCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst().orElseThrow(() -> new ValidateException(HttpStatus.BAD_REQUEST, "세션이 만료되었거나 유효하지 않습니다."));
    }

    private Cookie setTokenInCookie(String tokenName, String token) {
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) tokenProvider.getTokenExpiration(tokenName));
        cookie.setPath("/");
        return cookie;
    }

}
