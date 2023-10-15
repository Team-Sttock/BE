package management.sttock.common.oauth2.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json;charset=UTF-8");

        String jsonResponse = String.format("{\"error\": \"소셜 로그인 실패!\", \"message\": \"%s\"}", exception.getMessage());
        response.getWriter().write(jsonResponse);

        log.info("소셜 로그인에 실패했습니다. 에러 메시지 : {}", exception.getMessage());
    }
}