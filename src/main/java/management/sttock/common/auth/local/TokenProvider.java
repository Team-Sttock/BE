package management.sttock.common.auth.local;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import management.sttock.common.exception.TokenRefreshException;
import management.sttock.db.entity.RefreshToken;
import management.sttock.db.entity.User;
import management.sttock.db.repository.RefreshTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private final RefreshTokenRepository refreshTokenRepository;

    private final String secret;
    private final long tokenValidityInMilliseconds;

    private Key key;

    private final long refreshTokenValidityInSeconds;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
            @Value("${REFRESH_VALIDATE_SECONDS}") long refreshTokenValidityInSeconds,
            RefreshTokenRepository refreshTokenRepository) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds;
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public RefreshToken createRefreshToken(User user, Authentication authentication) {

        LocalDateTime issuedDt = LocalDateTime.now();
        LocalDateTime expiredDt = issuedDt.plusSeconds(tokenValidityInMilliseconds);

        String jwt = Jwts.builder()
                .setSubject(authentication.getName())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        RefreshToken refreshToken = new RefreshToken(user, jwt, issuedDt, expiredDt);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);
        return savedRefreshToken;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Collections.emptyList();

        org.springframework.security.core.userdetails.User principal =
                new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public String getUserIdFromToken(String token){
        String subject =  Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return subject;
    }

    /**
     * accessToken 갱신을 위한 validation 처리
     * 1. accessToken 유효성 검사(유효 여부)
     * 2. refreshToken 유효성 검사(만료, 유효 여부)
     * 3. 저장소에 refreshToken 존재 여부 & 소유자 검증
     */
    public void valicateForReIssue(RefreshToken refreshToken) {
             validateRefreshToken(refreshToken);
             checkExpirationDate(refreshToken);
             findByToken(refreshToken);
             checkTokenUser(refreshToken);
    }

    /**
     * accessToken 생성 로직
     */
    public String generateNewToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public boolean validateRefreshToken(RefreshToken refreshToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken.getToken());
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new TokenRefreshException(refreshToken.getToken(), "유효하지 않은 RefreshToken 입니다.");
        }
    }

    public boolean checkExpirationDate(RefreshToken refreshToken) {
        if(refreshToken.getExpiredDt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getToken(), "RefreshToken 이 만료되었습니다. 다시 로그인해 주십시오");
        }
        return true;
    }

    public boolean findByToken(RefreshToken refreshToken) {
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findRefreshTokenByToken(refreshToken.getToken());
        if(findRefreshToken.isEmpty()) {
            throw new TokenRefreshException(refreshToken.getToken(), "존재하지 않는 RefreshToken 입니다.");
        }
        return true;
    }

    public boolean checkTokenUser(RefreshToken refreshToken) {
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findRefreshTokenByToken(refreshToken.getToken());
        if (refreshToken.getUser().equals(findRefreshToken.get().getUser())) {
            throw new TokenRefreshException(refreshToken.getToken(), "소유자가 일치하지 않습니다.");
        }
        return true;
    }
}