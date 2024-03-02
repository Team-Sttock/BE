package management.sttock.api.sevice;

import management.sttock.api.dto.product.BasicProductInfo;

import java.util.List;

public interface ProductService {
    List<BasicProductInfo> selectBasicProducts(String like);

//    Page<BasicProductInfo> selectUserProducts(Pageable pageable, String category);
}
