package management.sttock.db.repository;

import management.sttock.db.entity.CommonCode;
import management.sttock.db.entity.enums.CommonCodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, String> {

    List<CommonCode> findByCodeType(CommonCodeType commonCodeType);

    CommonCode findByCode(String like);

}
