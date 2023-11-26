package management.sttock.db.repository;

import management.sttock.db.entity.StockMaster;
import management.sttock.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMasterRepository extends JpaRepository<StockMaster, Long> {
    Page<StockMaster> findByUser(User user, Pageable pageable);
}
