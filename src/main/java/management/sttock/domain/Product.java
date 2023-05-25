package management.sttock.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED) //상속-조인 전략 사용
@DiscriminatorColumn //dtype 자동생성
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String name;

    private String description; //상품 구체 설명(사용자 입력)

    private LocalDate purchaseDate;

    private int expectedPurchaseDate;

    private int purchaseStatus; //구매완료=0, 예측실패=1, 구매쉬기=2, 구매예정없음=4

    @Builder
    public Product(Category category, Member member, String name, String description,
                   LocalDate purchaseDate, int expectedPurchaseDate, int purchaseStatus) {
        this.category = category;
        this.member = member;
        this.name = name;
        this.description = description;
        this.purchaseDate = purchaseDate;
        this.expectedPurchaseDate = expectedPurchaseDate;
        this.purchaseStatus = purchaseStatus;
    }

    //==연관관계 편의 메소드==//
    public void setMember(Member member) {
        this.member = member;
        member.getProducts().add(this);
    }
    //현재 상품에 따른 카테고리 변경 불가능, 필요시 생성


}
