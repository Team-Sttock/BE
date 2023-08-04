package management.sttock.api.response.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import management.sttock.db.entity.Product;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TotalProductResponseDto {
    private Long id;
    private String category;
    private String name;
    private String description;
    private LocalDate purchaseDate;
    private int purchaseAmount;
    private LocalDate expectedDate;
    private int purchaseStatus;
    private Float regularCapacity;

    public TotalProductResponseDto(Product product) {
        this.id = product.getId();
        this.category = product.getCategory();
        this.name = product.getName();
        this.description = product.getDescription();
        this.purchaseDate = product.getPurchaseDate();
        this.expectedDate = product.getExpectedDate();
        this.purchaseAmount = product.getPurchaseAmount();
        this.purchaseStatus = product.getPurchaseStatus();
        this.regularCapacity = product.getRegularCapacity();
    }
}
