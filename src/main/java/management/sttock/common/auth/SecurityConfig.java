package management.sttock.common.auth;

import lombok.RequiredArgsConstructor;
import management.sttock.common.auth.local.*;
import management.sttock.common.oauth2.handler.OAuth2LoginFailureHandler;
import management.sttock.common.oauth2.handler.OAuth2LoginSuccessHandler;
import management.sttock.common.oauth2.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
//    private final JwtFilter jwtFilter;


    public static final String[] PERMIT_ALL_REQUESTS = {
                        "/h2-console/**"
                        ,"/favicon.ico"
                        ,"/error"
                        ,"/logout"
                        ,"/home", "/signup", "/login", "/user/loginId", "/user/password/recover", "/email"
                        , "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/api-docs/**"
                        , "/oauth2/**", "/", "/oauth2/*"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring()
//                .antMatchers(
//                        "/h2-console/**"
//                        ,"/favicon.ico"
//                        ,"/error"
//                        ,"/logout"
//                        ,"/home", "/signup", "/user/loginId", "/user/password/recover", "/email"
//                        , "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/api-docs/**"
//                        , "/oauth2/**", "/", "/oauth2/*"
//                )
//        ;
//    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(PERMIT_ALL_REQUESTS).permitAll()
                .anyRequest().authenticated()
                .and()
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)n // todo. jwtfiter 달아주세요
                .oauth2Login()
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandler)
                .redirectionEndpoint()
                .baseUri("/login/oauth2/*")
                .and()
                .userInfoEndpoint().userService(customOAuth2UserService);

        return http.build();
//        return http
//                .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/home", "/login", "/signup", "/user/loginId", "/user/password/recover", "/email").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/home")
//                .and()
//                // 소셜로그인
//                .oauth2Login()
//                .successHandler(oAuth2LoginSuccessHandler)
//                .failureHandler(oAuth2LoginFailureHandler)
//                .redirectionEndpoint()
//                .baseUri("/login/oauth2/*")
//                .and()
//                .userInfoEndpoint().userService(customOAuth2UserService)
//                .
//                ;

//        http.apply(new JwtSecurityConfig(tokenProvider));

    }
}
