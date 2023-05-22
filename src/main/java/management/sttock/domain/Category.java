package management.sttock.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
}
