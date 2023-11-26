package management.sttock.api.dto.stock;

import lombok.Data;
import management.sttock.db.entity.StockMaster;

import java.time.LocalDateTime;

@Data
public class BasicStockInfo {
    /**
     * 유저의 상품 리스트 조회시 사용하는 DTO
     */
    private Long prodId;
    private String prodName;
    private String categoryCd;
    private String categoryImageUrl;
    private LocalDateTime purchaseDate; // 구매 날짜
    private int expectedDays;
    private String state;

    public BasicStockInfo(StockMaster sm){
        this.prodId = sm.getProduct().getProductId();
        this.prodName = sm.getProduct().getName();
        this.categoryCd = sm.getProduct().getProductCategoryCd().getCode();
        this.purchaseDate = sm.getCrtDt();
        this.state = sm.getState().getCode();
    }
}
