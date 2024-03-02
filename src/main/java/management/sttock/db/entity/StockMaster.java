package management.sttock.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import management.sttock.db.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
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
    private LocalDateTime cycleEndDt; // 주기 종료 날짜
    private String useYn;
    private LocalDateTime expirationDt; // 유통기한
    @OneToMany(mappedBy = "stockMaster", cascade = CascadeType.ALL)
    private List<StockDetail> stockDetailList = new ArrayList<>();
    @Builder
    public StockMaster(User user, Product product, String nickname, Integer basicAmount, Integer basicUsage, Integer headCount, Integer buyQty, LocalDateTime cycleEndDt, String useYn, LocalDateTime buyDt, LocalDateTime expirationDt) {
        this.user = user;
        this.product = product;
        this.nickname = nickname;
        this.basicAmount = basicAmount;
        this.basicUsage = basicUsage;
        this.headCount = headCount;
        this.buyQty = buyQty;
        this.cycleEndDt = cycleEndDt;
        this.useYn = useYn;
        this.buyDt = buyDt;
        this.expirationDt = expirationDt;
    }
    public void addStockDetail(StockDetail stockDetail){
        this.stockDetailList.add(stockDetail);
        stockDetail.setStockMaster(this);
    }
}