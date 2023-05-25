package management.sttock.productDto;

import lombok.*;
import management.sttock.domain.Category;
import management.sttock.domain.CategoryType;
import management.sttock.domain.Product;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequestDto {

    @NotBlank
    private String token;
    @NotBlank
    private CategoryType categoryType;
    @NotBlank
    private String name;
    private String discription;

    private LocalDate purchaseDate;

    private int expectedPurchaseDate;

    @Builder
    public CreateProductRequestDto(CategoryType categoryType, String name, String discription,
                                   LocalDate purchaseDate, int expectedPurchaseDate) {
        this.categoryType = categoryType;
        this.name = name;
        this.discription = discription;
        this.purchaseDate = purchaseDate;
        this.expectedPurchaseDate = expectedPurchaseDate;
    }
    //dto->entity
    public Product toEntity() {
        return Product.builder()
                .category(toEntity().getCategory())
                .name(this.name)
                .description(this.discription)
                .purchaseDate(this.purchaseDate)
                .expectedPurchaseDate(this.expectedPurchaseDate)
                .build();
    }

}
