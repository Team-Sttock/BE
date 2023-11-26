package management.sttock.api.sevice;

import management.sttock.api.dto.product.ProductRequest;
import management.sttock.api.dto.stock.BasicStockInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;


public interface StockService {
    void insertStock(ProductRequest productRequest, Authentication authentication);

    Page<BasicStockInfo> getUserProducts(Pageable pageable, String category, Authentication authentication);
}
