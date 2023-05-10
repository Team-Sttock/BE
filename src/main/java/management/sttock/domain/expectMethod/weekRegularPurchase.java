package management.sttock.domain.expectMethod;

import lombok.Getter;
import management.sttock.domain.Product;

import javax.persistence.Entity;

@Getter
@Entity
public class weekRegularPurchase extends Product {
    private int aWeek;
}
