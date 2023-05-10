package management.sttock.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
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
}
