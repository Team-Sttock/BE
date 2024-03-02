package management.sttock.api.sevice;

import management.sttock.api.db.entity.enums.CommonCodeType;
import management.sttock.api.dto.common_code.BasicCommonCodeInfo;

import java.util.List;

public interface CommonCodeService {
    List<BasicCommonCodeInfo> selectBasicCommonCodes(CommonCodeType commonCodeType, String like);
}
