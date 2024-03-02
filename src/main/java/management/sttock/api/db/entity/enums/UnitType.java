package management.sttock.api.db.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnitType {
    PIECES("PIECES", "매"),
    COUNT("COUNT", "개"),
    mL("mL", "mL"),
    g("g", "g"),
    ;
    private String code;
    private String name;

}
