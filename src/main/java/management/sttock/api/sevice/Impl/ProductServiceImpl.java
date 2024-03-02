package management.sttock.api.sevice.Impl;

import lombok.RequiredArgsConstructor;
import management.sttock.api.db.entity.Product;
import management.sttock.api.db.repository.ProductRepository;
import management.sttock.api.db.repository.UserRepository;
import management.sttock.api.dto.product.BasicProductInfo;
import management.sttock.api.sevice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    private List<Product> selectProducts(String like){
        /* name 필터가 있는 전체 product 를 반환하는 함수 */
        if(like == null){
            return productRepository.findAll();
        }
        return productRepository.findByNameContaining(like);
    }
    @Override
    public List<BasicProductInfo> selectBasicProducts(String like){
        return selectProducts(like).stream().map(o -> new BasicProductInfo(o)).collect(Collectors.toList());
    }

}
