package management.sttock.domain.expectMethod;

import lombok.Getter;
import management.sttock.domain.Product;

import javax.persistence.Entity;

@Getter
@Entity
public class useByCount extends Product {
    private int regularDate; //갯수 - 1개 사용일
}
