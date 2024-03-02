package management.sttock.common.auth.local;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import management.sttock.api.db.entity.RefreshToken;
import management.sttock.api.db.entity.User;
import management.sttock.api.db.repository.RefreshTokenRepository;
import management.sttock.support.error.ApiException;
import management.sttock.support.error.ErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
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
            @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds,
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
    public String createToken(UserDetails userDetails) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim(AUTHORITIES_KEY, userDetails.getAuthorities())
                .signWith(key, SignatureAlgorithm.HS256)
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .compact();
    }
    public long getTokenExpiration(String tokenName) {
        if(tokenName.equals("accessToken")) return tokenValidityInMilliseconds;
        if (tokenName.equals("refreshToken")) return refreshTokenValidityInSeconds;
        return 0;
    }

    public RefreshToken createRefreshToken(User user, UserDetails userDetails){
        LocalDateTime issuedDt = LocalDateTime.now();
        LocalDateTime expiredDt = issuedDt.plusSeconds(refreshTokenValidityInSeconds);

        String jwt = Jwts.builder()
                .setSubject(userDetails.getUsername())
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

    private void valicateForReIssue(RefreshToken refreshToken) {
             validateRefreshToken(refreshToken);
             checkExpirationDate(refreshToken);
             findByToken(refreshToken);
             checkTokenUser(refreshToken);
    }

    public String renewToken(RefreshToken refreshToken){
        valicateForReIssue(refreshToken);
        return generateNewToken(); //여기 회원 정보 추용
    }

    private String generateNewToken() {
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
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .compact();
    }

    private boolean validateRefreshToken(RefreshToken refreshToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken.getToken());
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new ApiException(ErrorType.INVALID_REFRESHTOKEN);
        }
    }

    private boolean checkExpirationDate(RefreshToken refreshToken) {
        if(refreshToken.getExpiredDt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new ApiException(ErrorType.INVALID_REFRESHTOKEN);
        }
        return true;
    }

    private boolean findByToken(RefreshToken refreshToken) {
        List<RefreshToken> findRefreshToken = refreshTokenRepository.findByTokenOrderByExpiredDtDesc(refreshToken.getToken());

        if(findRefreshToken.isEmpty()) {
            throw new ApiException(ErrorType.INVALID_REFRESHTOKEN);
        }
        return true;
    }

    private boolean checkTokenUser(RefreshToken refreshToken) {
        RefreshToken findRefreshToken = refreshTokenRepository.findByTokenOrderByExpiredDtDesc(refreshToken.getToken()).get(0);
        boolean isNotEqual = !refreshToken.getUser().equals(findRefreshToken.getUser());
        if (isNotEqual) {
            throw new ApiException(ErrorType.INVALID_REFRESHTOKEN);
        }
        return true;
    }
}