package management.sttock.api.sevice;

import management.sttock.api.dto.product.BasicProductInfo;
import management.sttock.api.dto.product.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProductService {
    List<BasicProductInfo> selectBasicProducts(String like);

//    Page<BasicProductInfo> selectUserProducts(Pageable pageable, String category);
}
