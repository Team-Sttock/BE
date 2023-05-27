package management.sttock.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String name;

    private String description; //상품 구체 설명

    private LocalDate purchaseDate;

    private int expectedPurchaseDate;

    //구매완료=0, 예측실패=1, 구매쉬기=2, 구매예정없음=3
    private int purchaseStatus;

    //사용량 측정 방식
    private int aMonth;

    private int aWeek;

    private int regularDate; //갯수 - 1개 사용일

    private Float regularCapacity;//용량 - 1일 사용량

    @Builder
    public Product(Member member, Category category, String name, String description,
                   LocalDate purchaseDate, int expectedPurchaseDate, int purchaseStatus,
                   int aMonth, int aWeek, int regularDate, Float regularCapacity) {
        this.member = member;
        this.category = category;
        this.name = name;
        this.description = description;
        this.purchaseDate = purchaseDate;
        this.expectedPurchaseDate = expectedPurchaseDate;
        this.purchaseStatus = purchaseStatus;
        this.aMonth = aMonth;
        this.aWeek = aWeek;
        this.regularDate = regularDate;
        this.regularCapacity = regularCapacity;
    }

    //==연관관계 편의 메소드==//
    public void setMember(Member member) {
        this.member = member;
        member.getProducts().add(this);
    }
}
