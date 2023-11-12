package management.sttock.db.entity;

import lombok.*;
import management.sttock.db.entity.base.BaseEntity;
import management.sttock.db.entity.enums.CommonCodeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity(name = "common_code")
@DynamicInsert
@DynamicUpdate
public class CommonCode implements Serializable {
    @Id
    private String code;

    @Column(name = "name_kr")
    private String nameKr;

    @Enumerated(EnumType.STRING)
    private CommonCodeType codeType; // CATEGORY, UNIT..
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "del_yn")
    private String delYn;
}
