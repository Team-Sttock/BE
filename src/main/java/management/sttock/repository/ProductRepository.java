package management.sttock.repository;

import lombok.RequiredArgsConstructor;
import management.sttock.domain.Member;
import management.sttock.domain.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final EntityManager em;

    public void save(Product product) {
        if(product.getId() == null) {
            em.persist(product);
        } else {
            em.merge(product); //dirty checking으로 수정 필요
        }
    }

    public List<Product> findAllProducts(String userId) {
        String jpql = "SELECT p FROM Product p " +
                "JOIN p.member m " +
                "WHERE m.userId = :userId";

        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        query.setParameter("userId", userId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Product findOne(String userId, Long id) {
        String jpql = "SELECT p FROM Product p " +
                "JOIN p.member m " +
                "WHERE m.userId = :userId " +
                "AND p.id = :productId";

        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        query.setParameter("userId", userId);
        query.setParameter("productId", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void deleteOne(Long id) {
        Query query = em.createQuery("delete from Product p where p.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
