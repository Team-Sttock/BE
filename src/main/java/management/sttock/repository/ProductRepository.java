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

    //1전체&최신등록순 2전체&소진임박순 3카테고리&최신등록순 4 카테고리&소진임박순
    //카테고리
    public List<Product> findProductsByCategoryByRecent(String userId, String category) {
        String jpql = "SELECT p FROM Product p " +
                "JOIN FETCH p.member m " +
                "WHERE m.userId = :userId " +
                "AND p.category = :category " +
                "ORDER BY p.purchaseDate ASC";

        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        query.setParameter("userId", userId);
        query.setParameter("category", category);

        return query.getResultList();
    }
    public List<Product> findProductsByCategoryByShort(String userId, String category) {
        String jpql = "SELECT p FROM Product p " +
                "JOIN FETCH p.member m " +
                "WHERE m.userId = :userId " +
                "AND p.category = :category " +
                "ORDER BY p.expectedDate ASC";

        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        query.setParameter("userId", userId);
        query.setParameter("category", category);

        return query.getResultList();
    }

    //전체
    public List<Product> findAllProductsByRecent(String userId) {
        String jpql = "SELECT p FROM Product p " +
                "JOIN FETCH p.member m " +
                "WHERE m.userId = :userId " +
                "ORDER BY p.purchaseDate ASC";

        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        query.setParameter("userId", userId);

        return query.getResultList();
    }

    public List<Product> findAllProductsByShort(String userId) {
        String jpql = "SELECT p FROM Product p " +
                "JOIN FETCH p.member m " +
                "WHERE m.userId = :userId " +
                "ORDER BY p.expectedDate ASC";

        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        query.setParameter("userId", userId);

        return query.getResultList();
    }


    public Product findOne(String userId, Long id) {
        String jpql = "SELECT p FROM Product p " +
                "JOIN fetch p.member m " +
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
