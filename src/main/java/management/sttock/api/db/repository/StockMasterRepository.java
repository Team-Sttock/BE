package management.sttock.api.db.repository;

import management.sttock.api.db.entity.StockMaster;
import management.sttock.api.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMasterRepository extends JpaRepository<StockMaster, Long> {
    List<StockMaster> findByUser(User user);
}
