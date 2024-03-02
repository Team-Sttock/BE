package management.sttock.api.sevice.Impl;

import management.sttock.api.db.entity.enums.ProductCategory;
import management.sttock.api.dto.common_code.EnumTypeRes;
import management.sttock.api.sevice.EnumTypeService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnumTypeServiceImpl implements EnumTypeService {
    @Override
    public List<EnumTypeRes> selectProductCategory(String like){
        if (like == null){
            return Arrays.stream(ProductCategory.values())
                    .map(productCategory -> {return new EnumTypeRes(productCategory.getCode(), productCategory.getName());})
                    .collect(Collectors.toList());
        }
        return Arrays.stream(ProductCategory.values()).filter(productCategory -> {
            return productCategory.getName().contains(like);
        })
                .map(productCategory -> {return new EnumTypeRes(productCategory.getCode(), productCategory.getName());})
                .collect(Collectors.toList());
    }

}
