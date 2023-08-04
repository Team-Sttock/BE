package management.sttock.api.request.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import management.sttock.db.entity.Product;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProductRequestDto {
    @NotBlank
    private String category;
    @NotBlank
    private String name;
    private String discription;
    private LocalDate purchaseDate;
    private int purchaseAmount; //사용자 구매량
    private LocalDate expectedDate;
    private Float regularCapacity;//용량 - 1일 사용량

    //dto->entity
    public Product toEntity() {
        return Product.builder()
                .category(this.category)
                .name(this.name)
                .description(this.discription)
                .purchaseDate(this.purchaseDate)
                .purchaseAmount(this.purchaseAmount)
                .expectedDate(this.expectedDate)
                .regularCapacity(this.regularCapacity)
                .build();
    }
}
