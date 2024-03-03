package management.sttock.api.dto.product;

import lombok.Data;
import management.sttock.api.db.entity.StockMaster;
import management.sttock.api.db.entity.enums.State;

import java.time.LocalDateTime;

@Data
public class ProductDetailInfo {
    private Long prodId;
    private String categoryCd;
    private String prodNickname;
    private LocalDateTime purchaseDate;
    private int expectedDays;
    private int purchaseCapacity;
    private String capacityUnitCd;
    private int purchaseNumber;
    private int numberOfUser;
    private LocalDateTime expirationDate;
    private String state;

    /**
     * 유저별로 등록한 물품들과
     * 남은 날짜를 계산한 결과를 넣어 Response 반환
     * @param sm
     * @param expectedDays
     */

    public ProductDetailInfo(StockMaster sm, int expectedDays){
        this.prodId = sm.getProduct().getProductId();
        this.prodNickname = sm.getNickname();
        this.categoryCd = sm.getProduct().getProductCategoryCd().getName();
        this.purchaseDate = sm.getBuyDt();
        this.expectedDays = expectedDays;
        this.purchaseCapacity = sm.getBasicAmount();
        this.capacityUnitCd = sm.getProduct().getUnitCd().getName();
        this.purchaseNumber = sm.getBuyQty();
        this.numberOfUser = sm.getHeadCount();
        this.expirationDate = sm.getExpirationDt();
        this.state = sm.getState().getName(); //Todo: 수정예정
    }

}
