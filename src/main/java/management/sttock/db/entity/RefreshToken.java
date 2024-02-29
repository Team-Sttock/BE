package management.sttock.db.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refresh_token")
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "token")
    private String token;

    @Column(name = "issued_dt") //발급시간
    private LocalDateTime issuedDt;
    @Column(name = "expired_dt")
    private LocalDateTime expiredDt;

    public RefreshToken(User user, String token, LocalDateTime issuedDt, LocalDateTime expiredDt) {
        this.user = user;
        this.token = token;
        this.issuedDt = issuedDt;
        this.expiredDt = expiredDt;
    }
}