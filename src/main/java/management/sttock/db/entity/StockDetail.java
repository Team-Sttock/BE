package management.sttock.db.entity;

import lombok.EqualsAndHashCode;
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
public class StockDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long StockDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_master_id")
    private StockMaster stockMaster;

    @Column(name =  "use_amount")
    private Integer useAmount; // 사용량

}
