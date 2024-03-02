package management.sttock.api.controller.basic;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import management.sttock.api.db.entity.enums.CommonCodeType;
import management.sttock.api.db.entity.enums.ProductCategory;
import management.sttock.api.dto.common_code.BasicCommonCodeInfo;
import management.sttock.api.dto.common_code.EnumTypeRes;
import management.sttock.api.sevice.CommonCodeService;
import management.sttock.api.sevice.EnumTypeService;
import management.sttock.common.define.ApiPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPath.V1_BASIC_CATEGORY)
@RequiredArgsConstructor
public class BasicCategoryController {
    private final EnumTypeService enumTypeService;
    @Operation(description = "카테고리 리스트 반환 api ", summary = "like는 한글로 카테고리 검색")
    @GetMapping
    public ResponseEntity<List<EnumTypeRes>> getBasicCategory(@RequestParam(value = "like", required = false) String like){
        return new ResponseEntity<>(enumTypeService.selectProductCategory(like), HttpStatus.OK);
    }
}
