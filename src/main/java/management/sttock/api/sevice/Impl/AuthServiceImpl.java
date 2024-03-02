package management.sttock.api.sevice.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management.sttock.api.db.entity.RefreshToken;
import management.sttock.api.db.entity.User;
import management.sttock.api.db.repository.AuthRespository;
import management.sttock.api.db.repository.RefreshTokenRepository;
import management.sttock.api.dto.auth.CookieResponse;
import management.sttock.api.dto.auth.LoginRequest;
import management.sttock.api.sevice.AuthService;
import management.sttock.common.auth.local.CustomUserDetailsService;
import management.sttock.common.auth.local.TokenProvider;
import management.sttock.support.error.ApiException;
import management.sttock.support.error.ErrorType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Slf4j
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
            Optional<User> user = authRespository.findByLoginId(request.getLoginId());
            boolean isNotMatchPassword = !passwordEncoder.matches(request.getPassword(),
                    user.get().getPassword());

            if (isNotMatchPassword) {
                throw new NoSuchElementException();
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLoginId());
            String token = tokenProvider.createToken(userDetails);
            RefreshToken refreshToken = tokenProvider.createRefreshToken(user.get(), userDetails);

            refreshTokenRepository.save(refreshToken);
            ResponseCookie accessTokenInCookie = setTokenInCookie("accessToken", token);
            ResponseCookie refreshTokenInCookie = setTokenInCookie("refreshToken", refreshToken.getToken());
            return new CookieResponse(accessTokenInCookie, refreshTokenInCookie);

        } catch (NoSuchElementException e) {
            throw new ApiException(ErrorType.LOGIN_FAILED);
        } catch (Exception e) {
            throw new ApiException(ErrorType.SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            RefreshToken refreshToken = getRefreshToken(request);
            refreshTokenRepository.delete(refreshToken);

            Cookie[] cookies = request.getCookies();
            removeCookie(cookies, response, "accessToken");
            removeCookie(cookies, response, "refreshToken");
        } catch (Exception e) {
            throw new ApiException(ErrorType.SERVER_ERROR);
        }
    }
    private void removeCookie(Cookie[] cookies, HttpServletResponse response, String cookieName){
        if (cookies != null) {
            Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .findFirst()
                    .ifPresent(cookie -> {
                        cookie.setMaxAge(0); // 쿠키 만료
                        cookie.setPath("/"); // 경로 설정
                        response.addCookie(cookie);
                    });
        }
    }

    @Transactional
    @Override
    public CookieResponse refreshToken(HttpServletRequest request) {
        RefreshToken refreshToken = getRefreshToken(request);
        String renewToken = tokenProvider.renewToken(refreshToken);

        ResponseCookie accessTokenInCookie = setTokenInCookie("accessToken", renewToken);
        ResponseCookie refreshTokenInCookie = setTokenInCookie("refreshToken", refreshToken.getToken());
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
                .findFirst().orElseThrow(() -> new ApiException(ErrorType.INVALID_REFRESHTOKEN));
    }

    private ResponseCookie setTokenInCookie(String tokenName, String token) {
        return ResponseCookie.from(tokenName, token)
                .httpOnly(true)
                .maxAge(tokenProvider.getTokenExpiration(tokenName))
                .path("/")
                .sameSite("Lax")
                .build();
    }
}