package management.sttock.api.db.repository;

import management.sttock.api.db.entity.RefreshToken;
import management.sttock.api.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    List<RefreshToken> findByTokenOrderByExpiredDtDesc(String token);
    void deleteAllByUser(User user);
}
