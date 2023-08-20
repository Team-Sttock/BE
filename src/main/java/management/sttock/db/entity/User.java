package management.sttock.db.entity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname; //사용자 입력 id

    @Column(name = "password")
    private String password;

    private String name;

    @Column(name = "gender_cd")
    private int genderCd;

    private String email;
    @Column(name = "family_num")
    private int familyNum;

    private LocalDate birthday;

    public User(Long id, String nickname, String password, String name,
                int genderCd, String email, int familyNum, LocalDate birthday) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.genderCd = genderCd;
        this.email = email;
        this.familyNum = familyNum;
        this.birthday = birthday;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

}
