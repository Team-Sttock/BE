package management.sttock.productDto;

import lombok.*;
import management.sttock.domain.Product;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductRequestDto {

    @NotBlank
    private String category;
    @NotBlank
    private String name;
    private String discription;

    private LocalDate purchaseDate;
    private int purchaseAmount; //사용자 구매량
    private Float regularCapacity;//용량 - 1일 사용량

//    @Builder
//    public CreateProductRequestDto(Category category, String name, String discription,
//                                   LocalDate purchaseDate,int purchaseAmount, int expectedPurchaseDate,
//                                   int regularDate, Float regularCapacity) {
//        this.category = category;
//        this.name = name;
//        this.discription = discription;
//        this.purchaseDate = purchaseDate;
//        this.purchaseAmount = purchaseAmount;
//        this.expectedPurchaseDate = expectedPurchaseDate;
//        this.regularDate = regularDate;
//        this.regularCapacity = regularCapacity;
//    }
    //dto->entity
    public Product toEntity() {
        return Product.builder()
                .category(this.category)
                .name(this.name)
                .description(this.discription)
                .purchaseDate(this.purchaseDate)
                .purchaseAmount(this.purchaseAmount)
                .regularCapacity(this.regularCapacity)
                .build();
    }
}