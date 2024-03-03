package management.sttock.api.dto.stock;

import lombok.Data;
import management.sttock.api.db.entity.StockMaster;
import management.sttock.api.db.entity.enums.State;

import java.time.LocalDateTime;

@Data
public class BasicStockInfo {
    /**
     * 유저의 상품 리스트 조회시 사용하는 DTO
     */
    private Long smId;
    private Long prodId;
    private String prodName;
    private String categoryCd;
    private int remainDays;
    private String state;

    public BasicStockInfo(StockMaster sm){
        this.smId = sm.getStockMasterId();
        this.prodId = sm.getProduct().getProductId();
        this.prodName = sm.getProduct().getName();
        this.categoryCd = sm.getProduct().getProductCategoryCd().getCode();
        this.remainDays = sm.getBasicAmount(); // TODO: 남은 날짜 계산 로직 수정 예정
        this.state = sm.getState().getName();
    }
}
