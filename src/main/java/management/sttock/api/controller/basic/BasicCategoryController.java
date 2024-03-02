package management.sttock.api.controller.basic;

import lombok.RequiredArgsConstructor;
import management.sttock.api.db.entity.enums.CommonCodeType;
import management.sttock.api.dto.common_code.BasicCommonCodeInfo;
import management.sttock.api.sevice.CommonCodeService;
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
    @Autowired
    private CommonCodeService commonCodeService;
    @GetMapping
    public ResponseEntity<List<BasicCommonCodeInfo>> getBasicCategory(@RequestParam(value = "like", required = false) String like){
        return new ResponseEntity<>(commonCodeService.selectBasicCommonCodes(CommonCodeType.PRODUCT_CATEGORY, like), HttpStatus.OK);
    }
}
