package management.sttock.db.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "VerificationCode", timeToLive = 600)
public class VerificationCode {
    @Id
    private String email;
    private int authNumber;
    private boolean verificationStatus;

    public VerificationCode(String email, int authNumber, boolean verificationStatus) {
        this.email = email;
        this.authNumber = authNumber;
        this.verificationStatus = verificationStatus;
    }

    public void setVerificationStatus(boolean verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}
