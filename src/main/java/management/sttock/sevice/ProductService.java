package management.sttock.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.domain.Category;
import management.sttock.domain.Product;
import management.sttock.repository.ProductRepository;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

//    @Transactional
//    public void updateProduct(Long productId, String name, String description,
//                              LocalDate purchaseDate, int expectedPurchaseDate, int purchaseStatus) {
//        Product findProduct = productRepository.findOne(productId);
//
//    }

    public List<Product> findProducts() {
        return productRepository.findAll();
    }
    public Product findProduct(Long productId) {
        return productRepository.findOne(productId);
    }
}
