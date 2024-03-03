package management.sttock.api.db.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import management.sttock.api.db.entity.base.BaseEntity;
import management.sttock.api.db.entity.enums.State;
import management.sttock.api.dto.product.ProductRequest;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity(name = "stock_master")
@DynamicInsert
@DynamicUpdate
public class StockMaster extends BaseEntity {
    /**
     * 재고 정보 마스터
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockMasterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private String nickname; // 상품 별칭
    private LocalDateTime buyDt; // 구매 날짜
    private Integer basicAmount; // 입력 총량
    private Integer buyQty; // 구매 갯수
    private Integer headCount; // 사용인원
    private Integer basicUsage; // 입력 1일 사용량
    private String useYn;
    @Enumerated(EnumType.STRING)
    private State state;
    private LocalDateTime expirationDt; // 유통기한
    @OneToMany(mappedBy = "stockMaster", cascade = CascadeType.ALL)
    private List<StockDetail> stockDetailList = new ArrayList<>();
    @Builder
    public StockMaster(User user, Product product, String nickname, Integer basicAmount, Integer basicUsage, Integer headCount, Integer buyQty, String useYn, LocalDateTime buyDt, LocalDateTime expirationDt, State state) {
        this.user = user;
        this.product = product;
        this.nickname = nickname;
        this.basicAmount = basicAmount;
        this.basicUsage = basicUsage;
        this.headCount = headCount;
        this.buyQty = buyQty;
        this.useYn = useYn;
        this.buyDt = buyDt;
        this.expirationDt = expirationDt;
        this.state = state;
    }
    public void addStockDetail(StockDetail stockDetail){
        this.stockDetailList.add(stockDetail);
        stockDetail.setStockMaster(this);
    }

    public void update(ProductRequest pr){
        this.nickname = pr.getNickname();
        this.basicAmount = pr.getPurchaseCapacity();
        this.basicUsage = pr.getExpectedDays() * pr.getPurchaseCapacity()/ pr.getPurchaseNumber();
        this.headCount = pr.getNumberOfUser();
        this.buyQty = pr.getPurchaseNumber();
        this.expirationDt = pr.getExpirationDate();
        this.buyDt = pr.getBuyDt();
    }

    public void changeState(State state){
        this.state = state;
    }
}
