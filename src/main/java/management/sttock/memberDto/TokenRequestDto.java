package management.sttock.memberDto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class TokenRequestDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String token;

    //entity-> dto
    public TokenRequestDto(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
