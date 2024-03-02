package management.sttock.api.dto.product;

import lombok.Data;
import management.sttock.api.db.entity.Product;

@Data
public class BasicProductInfo {
    /**
     * 기본 Product List 반환 값
     */
    private Long prodId;
    private String prodName;
    private String categoryName;
    private float basicExpectedAmount; // 예상 소모 량
    private String basicExpectedUnit; // 단위

    public BasicProductInfo(Product product){
        this.prodId = product.getProductId();
        this.prodName = product.getName();
        this.categoryName = product.getProductCategoryCd().getName();
        this.basicExpectedAmount = product.getUsageCount();
        this.basicExpectedUnit = product.getUnitCd().getName();
    }

}
