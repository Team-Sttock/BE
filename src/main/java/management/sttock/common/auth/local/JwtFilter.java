package management.sttock.common.auth.local;

import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import management.sttock.support.error.ApiException;
import management.sttock.support.error.ErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String[] PERMIT_ALL_REQUESTS = {
            "/h2-console/**"
            ,"/favicon.ico"
            ,"/error"
            ,"/logout"
            ,"/home", "/signup", "/api/v1/auth/login", "/api/v1/user/check", "/api/v1/user/loginId", "/api/v1/user/temp-password", "/api/v1/user/email"
            , "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/api-docs/**"
            , "/oauth2/**", "/oauth2/*"
    };
    private final TokenProvider tokenProvider;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        logger.info("do Filter");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();

        if (Arrays.stream(PERMIT_ALL_REQUESTS).anyMatch(requestURI::startsWith)) {
            logger.info("permit: " + requestURI);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String jwt = resolveToken(httpServletRequest);

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst().orElseThrow(() -> new ApiException(ErrorType.INVALID_ACCESSTOKEN));
    }
}