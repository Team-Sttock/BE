package management.sttock.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Entity(name = "user_role")
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    private int code; //일반 사용자 1, 관리자 2

    @Column(name = "kr_name")
    private String krName;

    @Builder
    public Role(int code) {
        this.code = code;
    }
}
