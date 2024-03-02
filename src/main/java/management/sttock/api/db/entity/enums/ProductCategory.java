package management.sttock.api.db.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategory {
    HOUSEHOLD("HOUSEHOLD", "생활용품"),
    BATHROOM("BATHROOM", "욕실용품"),
    HAIR_BODY("HAIR_BODY", "헤어/바디") ,
    SKIN_CARE("SKIN_CARE", "스킨케어"),
    WOMEN_PRODUCT("WOMEN_PRODUCT", "여성용품"),
    TOOTH_CLEANER("TOOTH_CLEANER", "구강용품/면도"),
    KITCHEN("KITCHEN", "주방용품"),
    BABY_CARE("BABY_CARE", "육아용품"),
    CUSTOM_CATEGORY("CUSTOM_CATEGORY", "직접추가")
    ;

    private String code;
    private String name;

}
