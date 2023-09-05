package management.sttock.db.entity;

import lombok.*;
import management.sttock.api.dto.user.UserInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login_id")
    private String loginId; //사용자 입력 id

    private String password;

    private String name;

    @Column(name = "gender_cd")
    private int genderCd;

    private String email;
    @Column(name = "family_num")
    private int familyNum;

    private Date birthday;

    @OneToMany(mappedBy = "user")
    private List<RefreshToken> refreshTokens = new ArrayList<>();

    public User(Long id, String loginId, String password, String name,
                int genderCd, String email, int familyNum, Date birthday) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.genderCd = genderCd;
        this.email = email;
        this.familyNum = familyNum;
        this.birthday = birthday;
    }

    public void updateUser(final UserInfo request, Date birthday){
        this.loginId = request.getLoginId();
        this.name = request.getName();
        this.genderCd = request.getGenderCd();
        this.email = request.getEmail();
        this.familyNum = request.getFamilyNum();
        this.birthday = birthday;
    }
    public void updatePassword(String password){
        this.password = password;
    }
}
