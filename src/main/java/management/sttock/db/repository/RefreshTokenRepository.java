package management.sttock.db.repository;

import management.sttock.db.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findRefreshTokenByToken(String token); //이거 쓰는 코드 리펙토링
    List<RefreshToken> findAllRefreshTokenByToken(String token);
    List<RefreshToken> findByTokenOrderByExpiredDtDesc(String token);
}