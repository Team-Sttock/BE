package management.sttock.domain.expectMethod;

import lombok.Getter;
import management.sttock.domain.Product;

import javax.persistence.Entity;

@Getter
@Entity
public class useByCapacity extends Product {
    private float regularCapacity;//용량 - 1일 사용량
}
