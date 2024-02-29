package management.sttock.api.dto.product;

import lombok.Data;
import management.sttock.db.entity.Product;

@Data
public class BasicProductInfo {
    /**
     * 기본 Product List 반환 값
     */
    private String prodName;
    private String categoryCd;
    private Integer basicExpectedAmount; // 예상 소모 량
    private String basicExpectedUnit; // 단위

    public BasicProductInfo(Product product){
        this.prodName = product.getName();
        this.categoryCd = product.getProductCategoryCd().getCode();
        this.basicExpectedAmount = product.getUsageCount();
        this.basicExpectedUnit = product.getUnitCd().getName();
    }

}
