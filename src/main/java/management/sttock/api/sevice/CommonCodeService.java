package management.sttock.api.sevice;

import management.sttock.api.dto.common_code.BasicCommonCodeInfo;
import management.sttock.db.entity.CommonCode;
import management.sttock.db.entity.enums.CommonCodeType;

import java.util.List;

public interface CommonCodeService {
    List<BasicCommonCodeInfo> selectBasicCommonCodes(CommonCodeType commonCodeType, String like);
}
