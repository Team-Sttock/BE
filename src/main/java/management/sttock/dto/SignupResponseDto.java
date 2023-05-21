package management.sttock.dto;

import lombok.*;
import management.sttock.domain.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class SignupResponseDto {
    @NotBlank
    @Size(min = 3, max = 50)
    private Long id;
    @NotBlank
    private String token;
    @NotBlank
    private String name;

    //entity -> dto
    public SignupResponseDto(Member member, String token) {
        this.id = member.getId();
        this.name = member.getName();
        this.token = token;
    }
}
