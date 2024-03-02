package management.sttock.api.dto.common_code;

import lombok.Data;
import management.sttock.db.entity.CommonCode;

@Data
public class BasicCommonCodeInfo {
    private String categoryCd;
    private String categoryUrl;
    private String categoryName;

    public BasicCommonCodeInfo(CommonCode commonCode){
        categoryCd = commonCode.getCode();
        categoryUrl = commonCode.getImageUrl();
        categoryName = commonCode.getNameKr();
    }
}