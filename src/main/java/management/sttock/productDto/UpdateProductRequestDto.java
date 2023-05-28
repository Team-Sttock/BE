package management.sttock.productDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import management.sttock.domain.Product;

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

    private Float regularCapacity;//용량 - 1일 사용량

    @Builder
    public UpdateProductRequestDto(String category, String name, String discription,
                                   LocalDate purchaseDate, Float regularCapacity) {
        this.category = category;
        this.name = name;
        this.discription = discription;
        this.purchaseDate = purchaseDate;
        this.regularCapacity = regularCapacity;
    }

    //dto->entity
    public Product toEntity() {
        return Product.builder()
                .category(this.category)
                .name(this.name)
                .description(this.discription)
                .purchaseDate(this.purchaseDate)
                .regularCapacity(this.regularCapacity)
                .build();
    }
}
