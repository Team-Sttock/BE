package management.sttock.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.domain.Product;
import management.sttock.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Long create(Product product) {
        productRepository.save(product);
        return product.getId();
    }
}
