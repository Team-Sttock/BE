package management.sttock.api.dto.product;

import lombok.Builder;
import lombok.Data;
import management.sttock.db.entity.CommonCode;
import management.sttock.db.entity.Product;
import management.sttock.db.entity.StockMaster;
import management.sttock.db.entity.User;

import java.time.LocalDateTime;

@Data
public class ProductRequest {
    private Long prodId;
    private String categoryCd;// 물건 카테고리
    private String prodNickname;// 닉네임
    private LocalDateTime purchaseDate; // 구매날짜
    private int purchaseCapacity;//구매용량
    private String capacityUnitCd;//구매 단위
    private int purchaseNumber;//구매 갯수
    private int expectedDays;// 예상 사용 일수
    private int numberOfUser;//사용 인원
    private LocalDateTime expirationDate;//유통기한

    public StockMaster toEntity(Product product, User user, CommonCode stateCode){
        StockMaster stockmaster = StockMaster.builder()
                                                .user(user)
                                                .product(product)
                                                .nickname(prodNickname)
                                                .state(stateCode)
                                                .basicAmount(purchaseCapacity)
                                                .basicUsage(purchaseCapacity * purchaseNumber / expectedDays)
                                                .headCount(numberOfUser)
                                                .buyQty(purchaseNumber)
                                                .cycleEndDt(LocalDateTime.now().plusDays(expectedDays))
                                                .useYn("Y")
                                                .expirationDt(expirationDate)
                                                .build();
        stockmaster.setCrtBy(user);
        return stockmaster;
    }

}
