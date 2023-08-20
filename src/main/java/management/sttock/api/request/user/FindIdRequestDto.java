package management.sttock.api.request.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindIdRequestDto {
    private String email;
}
