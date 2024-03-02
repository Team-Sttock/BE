package management.sttock.api.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import management.sttock.api.db.entity.base.BaseEntity;
import management.sttock.api.db.entity.enums.ProductCategory;
import management.sttock.api.db.entity.enums.UnitType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

    private Float usageCount; // 1일 사용양

}
