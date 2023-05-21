package management.sttock.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String userId; //사용자 입력 id

    private String userPassword;

    private String name;
    private String email;

    private int phoneNumber;

    @OneToMany(mappedBy = "member")
    private List<Product> products = new ArrayList<>();

    @Builder
    public Member(String userId, String userPassword, String name, String email, int phoneNumber) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}
