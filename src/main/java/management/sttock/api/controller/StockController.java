package management.sttock.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import management.sttock.api.dto.message.SuccessMessage;
import management.sttock.api.dto.product.ProductDetailInfo;
import management.sttock.api.dto.product.ProductRequest;
import management.sttock.api.dto.stock.BasicStockInfo;
import management.sttock.api.sevice.StockService;
import management.sttock.common.define.ApiPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Stock", description = "개인 유저가 보유한 PRODUCT API")
@RestController
@RequestMapping(ApiPath.V1_STOCK)
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    public ResponseEntity<Page<BasicStockInfo>> getProducts(@RequestParam(defaultValue = "0", value = "page") int page,
                                                            @RequestParam(defaultValue = "10", value = "size") int size,
                                                            @RequestParam(defaultValue = "lasted", value = "sorted") String sortBy,
                                                            @RequestParam(value = "category", required = false) String category,
                                                            @RequestParam(required = false) Long userId, // Todo: 삭제예정
                                                            Authentication authentication) {
        Pageable pageable = PageRequest.of(page, size, stockService.getSortedBy(sortBy));
        return new ResponseEntity<>(stockService.getUserProducts(pageable, category, authentication, userId), HttpStatus.OK);
    }

    @Operation(summary = "상품 추가", description = "유저 상품 정보 추가")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ProductRequest productRequest, Authentication authentication) {
        stockService.insertStock(productRequest, authentication);
        return new ResponseEntity<>(new SuccessMessage(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailInfo> getProductsDetail(@PathVariable Long id, Authentication authentication){
        return new ResponseEntity<>(stockService.getProductDetail(id,authentication), HttpStatus.OK);
    }
}
