package management.sttock.productDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TotalProductResponseDto {
    private String category;
    private String name;
    private String discription;
    private LocalDate purchaseDate;
    private int purchaseAmount;
    private int purchaseStatus;
    private Float regularCapacity;
}
