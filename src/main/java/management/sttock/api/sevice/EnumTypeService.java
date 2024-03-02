package management.sttock.api.sevice;

import management.sttock.api.dto.common_code.EnumTypeRes;

import java.util.List;

public interface EnumTypeService {
    List<EnumTypeRes> selectProductCategory(String like);
}
