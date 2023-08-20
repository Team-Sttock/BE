package management.sttock.api.response.user;

import lombok.*;
import management.sttock.db.entity.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class SignupResponseDto {
    @NotBlank
    @Size(min = 3, max = 50)
    private Long id;

    //entity -> dto
    public SignupResponseDto(Member member) {
        this.id = member.getId();
    }
}
