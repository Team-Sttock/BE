package management.sttock.db.entity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String userId; //사용자 입력 id

    private String userPassword;

    private String name;
    private String email;

    private String phoneNumber;

    @OneToMany(mappedBy = "member")
    private List<Product> products = new ArrayList<>();

    @Builder
    public Member(String userId, String userPassword, String name, String email, String phoneNumber) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.userPassword = passwordEncoder.encode(userPassword);
    }

}
