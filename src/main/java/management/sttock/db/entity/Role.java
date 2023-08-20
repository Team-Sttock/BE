package management.sttock.db.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "user_role")
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    private int code;
}
