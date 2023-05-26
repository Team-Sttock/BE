package management.sttock.productDto;

import lombok.*;
import management.sttock.domain.Category;
import management.sttock.domain.Product;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class CreateProductRequestDto {

    @NotBlank
    private Category category;
    @NotBlank
    private String name;
    private String discription;

    private LocalDate purchaseDate;

    private int expectedPurchaseDate;

    private int aMonth;

    private int aWeek;

    private int regularDate; //갯수 - 1개 사용일

    private Float regularCapacity;//용량 - 1일 사용량

    @Builder
    public CreateProductRequestDto(Category category, String name, String discription,
                                   LocalDate purchaseDate, int expectedPurchaseDate,
                                   int aMonth, int aWeek, int regularDate, Float regularCapacity) {
        this.category = category;
        this.name = name;
        this.discription = discription;
        this.purchaseDate = purchaseDate;
        this.expectedPurchaseDate = expectedPurchaseDate;
        this.aMonth = aMonth;
        this.aWeek = aWeek;
        this.regularDate = regularDate;
        this.regularCapacity = regularCapacity;
    }
    //dto->entity
    public Product toEntity() {
        return Product.builder()
                .category(this.category)
                .name(this.name)
                .description(this.discription)
                .purchaseDate(this.purchaseDate)
                .expectedPurchaseDate(this.expectedPurchaseDate)
                .aMonth(this.aMonth)
                .aWeek(this.aWeek)
                .regularDate(this.regularDate)
                .regularCapacity(this.regularCapacity)
                .build();
    }
}
