package management.sttock.api.dto.product;


import lombok.Getter;
import management.sttock.api.db.entity.Product;
import management.sttock.api.db.entity.StockMaster;
import management.sttock.api.db.entity.User;
import management.sttock.api.db.entity.enums.State;

import java.time.LocalDateTime;

@Getter
public class ProductRequest {
    private Long prodId;
    private String nickname;// 닉네임
    private LocalDateTime purchaseDate; // 구매날짜
    private int purchaseCapacity;//구매용량
    private int purchaseNumber;//구매 갯수
    private int expectedDays;// 예상 사용 일수
    private int numberOfUser;//사용 인원
    private LocalDateTime expirationDate;//유통기한
    private LocalDateTime buyDt;// 구매날짜
    private Long userId; // Todo: auth 달면 삭제 예정, 임시 userId;

    public StockMaster toEntity(Product product, User user){
        StockMaster stockmaster = StockMaster.builder()
                                                .user(user)
                                                .product(product)
                                                .nickname(nickname)
                                                .basicAmount(purchaseCapacity)
                                                .basicUsage(expectedDays * purchaseCapacity / purchaseNumber )
                                                .headCount(numberOfUser)
                                                .buyQty(purchaseNumber)
                                                .state(State.INUSE)
                                                .useYn("Y")
                                                .expirationDt(expirationDate)
                                                .buyDt(buyDt)
                                                .build();
        stockmaster.setCrtBy(user);
        return stockmaster;
    }

}
