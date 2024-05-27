package ex.boards.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupReqDto {

    private String nickname;
    private String email;
    private String password;
}
