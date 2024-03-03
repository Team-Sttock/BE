package management.sttock.api.sevice;

import management.sttock.api.dto.product.ProductDetailInfo;
import management.sttock.api.dto.product.ProductRequest;
import management.sttock.api.dto.stock.BasicStockInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


public interface StockService {

    @Transactional
    void insertStock(ProductRequest productRequest, Authentication authentication);

    @Transactional
    Long update(ProductRequest productRequest, Long smId, Authentication authentication);

    ProductDetailInfo getProductDetail(Long id, Authentication authentication, Long userId);

    Page<BasicStockInfo> getUserProducts(Pageable pageable, String category, LocalDateTime startDt, LocalDateTime endDt, Authentication authentication, Long userId);

    Sort getSortedBy(String sortBy);

    void stopUsing(Long smId, Authentication authentication);
}
