package management.sttock.api.dto.common_code;

import lombok.Getter;

@Getter
public class EnumTypeRes {
    private String code;
    private String name;
    public EnumTypeRes(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
