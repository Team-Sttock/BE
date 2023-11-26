package management.sttock.api.dto.sm;

import lombok.Data;
import management.sttock.db.entity.StockMaster;

import java.time.LocalDateTime;

@Data
public class ProductInfo {
    /**
     * 			"prod_id": 123
     * 			"prod_name": "트리트먼트",
     * 			"category_cd": "ETC" // 대문자로 올 예정
     * 			"category_image_url" : "https://s3.com/urljkdfa",
     * 			"purchase_date" : "yyyy-MM-dd'T'HH:mm:ss"
     * 			"expected_days" : 200, // 예상한 기간 ( days ) -> 프론트 산
     * 			"state" : "in-use"
     */
    private Long prodId;
    private String prodName;
    private String categoryCd;
    private String categoryImageUrl;
    private LocalDateTime purchaseDate;
    private int expectedDays;
    private String state;

    public ProductInfo(StockMaster sm){
        this.prodId = sm.getProduct().getProductId();
        this.prodName = sm.getProduct().getName();
        this.categoryCd = sm.getProduct().getProductCategoryCd().getCode();
        this.categoryImageUrl = sm.getProduct().getProductCategoryCd().getImageUrl();
        this.purchaseDate = sm.getCrtDt();
        this.expectedDays = sm.getBasicAmount() / sm.getBasicUsage();
        this.state = sm.getState().getCode();
    }
}
