package management.sttock.db.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class CustomStockRepositoryImpl implements CustomStockRepository{
    @PersistenceContext
    private EntityManager em;
}
