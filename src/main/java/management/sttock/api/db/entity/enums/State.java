package management.sttock.api.db.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
    INUSE("INUSE", "사용중"),
    EXHAUSTED("EXHAUSTED", "소진"),
    STOP("STOP", "사용중지"),
    DELETE("DELETE", "삭제"),
    ;
    private String code;
    private String name;
}
