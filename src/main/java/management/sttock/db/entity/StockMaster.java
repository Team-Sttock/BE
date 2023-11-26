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

    private String nickname;

    @JsonIgnore
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "state", referencedColumnName = "code")
    private CommonCode state; // 상태값

    @Column(name = "basic_amount")
    private Integer basicAmount; // 입력 총량

    @Column(name = "basic_usage")
    private Integer basicUsage; // 입력 1일 사용량

    @Column(name = "head_count")
    private Integer headCount; // 사용인원

    @Column(name = "buy_qty")
    private Integer buyQty; // 구매 갯수

    @Column(name = "cycle_end_dt")
    private LocalDateTime cycleEndDt;

    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "expiration_dt")
    private LocalDateTime expirationDt; // 유통기한

    @OneToMany(mappedBy = "stockMaster", cascade = CascadeType.ALL)
    private List<StockDetail> stockDetailList = new ArrayList<>();

    @Builder
    public StockMaster(User user, Product product, String nickname, CommonCode state, Integer basicAmount, Integer basicUsage, Integer headCount, Integer buyQty, LocalDateTime cycleEndDt, String useYn, LocalDateTime expirationDt) {
        this.user = user;
        this.product = product;
        this.nickname = nickname;
        this.state = state;
        this.basicAmount = basicAmount;
        this.basicUsage = basicUsage;
        this.headCount = headCount;
        this.buyQty = buyQty;
        this.cycleEndDt = cycleEndDt;
        this.useYn = useYn;
        this.expirationDt = expirationDt;
    }
}
