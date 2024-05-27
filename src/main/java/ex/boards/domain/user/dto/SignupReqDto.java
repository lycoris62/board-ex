package ex.boards.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class SignupReqDto {

    private String nickname;
    private String email;
    private String password;
}
