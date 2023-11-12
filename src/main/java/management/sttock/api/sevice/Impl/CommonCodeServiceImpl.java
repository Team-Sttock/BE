package management.sttock.api.sevice.Impl;

import lombok.RequiredArgsConstructor;
import management.sttock.api.dto.common_code.BasicCommonCodeInfo;
import management.sttock.api.sevice.CommonCodeService;
import management.sttock.db.entity.CommonCode;
import management.sttock.db.entity.enums.CommonCodeType;
import management.sttock.db.repository.CommonCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonCodeServiceImpl implements CommonCodeService {
    @Autowired
    private CommonCodeRepository commonCodeRepository;
    /**
     *
     * @param commonCodeType :: code
     * @param CommonCodeType enum 타입, like :: 찾는 조건
     * @return
     */
    private List<CommonCode> selectCommonCodes(CommonCodeType commonCodeType, String like){
        return commonCodeRepository.findByCodeType(commonCodeType);
    }
    @Override
    public List<BasicCommonCodeInfo> selectBasicCommonCodes(CommonCodeType commonCodeType, String like){
        return selectCommonCodes(commonCodeType, like).stream().map(o -> new BasicCommonCodeInfo(o)).collect(Collectors.toList());
    }
}
