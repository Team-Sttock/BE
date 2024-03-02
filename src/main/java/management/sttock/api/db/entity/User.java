package management.sttock.api.db.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import management.sttock.api.db.entity.enums.SocialType;
import management.sttock.api.dto.user.UserInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login_id")
    private String loginId; //사용자 입력 id

    private String password;

    private String name;

    private int genderCd;

    private String email;
    private int familyNum;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    @OneToMany(mappedBy = "user")
    private List<RefreshToken> refreshTokens = new ArrayList<>();


    public User(Long id, String loginId, String password, String name,
                int genderCd, String email, int familyNum, LocalDate birthday) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.genderCd = genderCd;
        this.email = email;
        this.familyNum = familyNum;
        this.birthday = birthday;
    }

    public void updateUser(final UserInfo request, LocalDate birthday){
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
