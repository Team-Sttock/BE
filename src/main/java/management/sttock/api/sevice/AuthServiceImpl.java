package management.sttock.api.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.api.request.user.LoginRequest;
import management.sttock.api.response.auth.CookieResponse;
import management.sttock.common.auth.local.TokenProvider;
import management.sttock.common.exception.ValidateException;
import management.sttock.db.entity.RefreshToken;
import management.sttock.db.entity.User;
import management.sttock.db.repository.AuthRespository;
import management.sttock.db.repository.RefreshTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
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
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Override
    @Transactional
    public CookieResponse login(LoginRequest request) {
        try {

            Optional<User> user = authRespository.findByNickname(request.getNickname());
            boolean isNotMatchPassword = !passwordEncoder.matches(request.getPassword(), user.get().getPassword());

            if (isNotMatchPassword) {
                throw new ValidateException(HttpStatus.UNAUTHORIZED, "비밀번호를 잘못 입력했습니다.");
            }

            //아직 role 추가 안함
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.getNickname(), request.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            String token = tokenProvider.createToken(authentication);
            RefreshToken refreshToken = tokenProvider.createRefreshToken(user.get(), authentication);
            refreshTokenRepository.save(refreshToken);
            Cookie accessTokenInCookie = setTokenInCookie("accessToken", token);
            Cookie refreshTokenInCookie = setTokenInCookie("refreshToken", String.valueOf(refreshToken));
            return new CookieResponse(accessTokenInCookie, refreshTokenInCookie);
        } catch (NoSuchElementException e) {
            throw new ValidateException(HttpStatus.UNAUTHORIZED, "등록되지 않은 아이디이거나, 아이디를 잘못 입력했습니다.");
        }
    }

    private Cookie setTokenInCookie(String tokenName, String token) {
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) tokenProvider.getTokenExpiration(tokenName));
        cookie.setPath("/");
        return cookie;
    }

}
