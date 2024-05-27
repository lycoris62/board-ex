package ex.boards.domain.user.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("빌더 패턴으로 유저 생성하는 테스트")
    @Test
    void create_user() {
        User user = User.builder()
            .nickname("jaeyun01")
            .email("jaeyun01@naver.com")
            .password("qwer1234")
            .build();

        assertThat(user.getNickname()).isEqualTo("jaeyun01");
        assertThat(user.getEmail()).isEqualTo("jaeyun01@naver.com");
        assertThat(user.getPassword()).isEqualTo("qwer1234");
    }
}