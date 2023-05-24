package management.sttock.repository;

import lombok.RequiredArgsConstructor;
import management.sttock.domain.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
    public List<Product> findAll() {
        return em.createQuery("select p from Product p", Product.class)
                .getResultList();
    }

    public Product findOne(Long id) {
        return em.find(Product.class, id);
    }

    //자주 이용하는 상품 조회 추가 필요

}
