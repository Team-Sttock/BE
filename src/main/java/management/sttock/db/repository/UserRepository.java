package management.sttock.db.repository;


import management.sttock.db.entity.User;
import management.sttock.db.entity.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByEmail(String email);
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

}
