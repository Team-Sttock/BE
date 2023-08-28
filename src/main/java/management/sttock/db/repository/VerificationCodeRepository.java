package management.sttock.db.repository;

import management.sttock.db.entity.VerificationCode;
import org.springframework.data.repository.CrudRepository;

public interface VerificationCodeRepository extends CrudRepository<VerificationCode, String> {
}
