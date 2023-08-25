package management.sttock.db.entity;

import lombok.*;
import management.sttock.api.dto.user.UserInfo;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname; //사용자 입력 id

    private String password;

    private String name;

    @Column(name = "gender_cd")
    private int genderCd;

    private String email;
    @Column(name = "family_num")
    private int familyNum;

    private Date birthday;

    public User(Long id, String nickname, String password, String name,
                int genderCd, String email, int familyNum, Date birthday) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.genderCd = genderCd;
        this.email = email;
        this.familyNum = familyNum;
        this.birthday = birthday;
    }

    public void updateUser(final UserInfo request, Date birthday){
        this.nickname = request.getNickname();
        this.name = request.getName();
        this.genderCd = request.getGenderCd();
        this.email = request.getEmail();
        this.familyNum = request.getFamilyNum();
        this.birthday = birthday;
    }
}
