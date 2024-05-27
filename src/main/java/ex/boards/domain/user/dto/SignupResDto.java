package ex.boards.domain.user.dto;

import ex.boards.domain.user.entity.User;
import lombok.Getter;

@Getter
public class SignupResDto {

    private final String nickname;
    private final String email;

    public SignupResDto(User user) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
