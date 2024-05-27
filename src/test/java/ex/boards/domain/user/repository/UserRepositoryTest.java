package ex.boards.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ex.boards.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @DisplayName("이메일로 유저 검색 성공")
    @Test
    void find_by_email_success() {
        // given
        User user = User.builder()
            .nickname("yunjae01")
            .email("yunjae01@naver.com")
            .password("qwer1234")
            .build();

        userRepository.save(user);

        // when
        User findUser = userRepository.findByEmail("yunjae01@naver.com").orElseThrow();

        // then
        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
    }
}