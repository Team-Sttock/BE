package management.sttock.common.oauth2.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management.sttock.common.auth.local.CustomUserDetailsService;
import management.sttock.common.auth.local.TokenProvider;
import management.sttock.common.oauth2.CustomOAuth2User;
import management.sttock.api.db.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private static final String HEADER_NAME = "Authorization";
    private static final String SCHEME = "Bearer";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(oAuth2User.getEmail());
        String accessToken= tokenProvider.createToken(userDetails);

        response.addHeader("Authorization", "Bearer " + accessToken);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(HEADER_NAME, accessToken);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // User의 Role이 MEMBER일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if(oAuth2User.getRole() == new Role(1)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(oAuth2User.getEmail());
                String accessToken= tokenProvider.createToken(userDetails);
                response.addHeader("Authorization", "Bearer " + accessToken);
                response.sendRedirect("oauth2/sign-up"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트

                // AccessToken 헤더에 실어서 보내기
                response.setStatus(HttpServletResponse.SC_OK);
                response.setHeader(HEADER_NAME, accessToken);
            } else {
                loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
