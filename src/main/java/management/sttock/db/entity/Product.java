package management.sttock.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import management.sttock.db.entity.base.BaseEntity;
import management.sttock.db.entity.enums.ProductCategory;
import management.sttock.db.entity.enums.UnitType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Table(name = "product")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategoryCd;

    private String name;

    @Enumerated(EnumType.STRING)
    private UnitType unitCd; // 단위

    private Integer amount; // 제품양

    private Integer usageCount; // 1일 사용양

}
