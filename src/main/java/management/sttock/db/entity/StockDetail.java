package management.sttock.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import management.sttock.db.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity(name = "stock_detail")
@DynamicInsert
@DynamicUpdate
@Getter
public class StockDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long StockDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sm_id")
    private StockMaster stockMaster;

    private Integer useAmount; // 사용량

    public void setStockMaster(StockMaster stockMaster) {
        this.stockMaster = stockMaster;
    }
}
