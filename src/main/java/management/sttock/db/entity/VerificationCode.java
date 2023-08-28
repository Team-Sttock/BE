package management.sttock.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "VerificationCode", timeToLive = 600)
public class VerificationCode {
    @Id
    private String email;
    private int authNumber;

    public VerificationCode(String email, int authNumber) {
        this.email = email;
        this.authNumber = authNumber;
    }
}
