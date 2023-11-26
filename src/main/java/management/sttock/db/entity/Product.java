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
@Entity(name = "product")
@DynamicInsert
@DynamicUpdate
@Getter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_cd", referencedColumnName = "code")
    private CommonCode productCategoryCd;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_cd", referencedColumnName = "code")
    private CommonCode unitCd; // 단위

    private Integer amount; // 제품양 :: 추후 사용 예정

    @Column(name = "product_usage")
    private Integer productUsage; // 한사람이 사용하는 제품 양

//    @ManyToOne() // updated_user_product 와 연관관계 추가 예정
}
