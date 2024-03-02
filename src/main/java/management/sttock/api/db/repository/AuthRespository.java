package management.sttock.api.db.repository;

import management.sttock.api.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRespository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
}
