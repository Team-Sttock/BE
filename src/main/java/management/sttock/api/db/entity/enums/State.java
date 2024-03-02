package management.sttock.api.db.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
    USING("USING", "사용중"),
    EXHAUST("EXHAUST", "소진"),
    STOP_USING("STOP_USING", "사용중지"),
    DELETE("DELETE", "삭제"),
    ;
    private String code;
    private String name;
}
