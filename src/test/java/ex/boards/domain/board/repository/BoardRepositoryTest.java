package ex.boards.domain.board.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ex.boards.domain.board.entity.Board;
import ex.boards.domain.user.entity.User;
import ex.boards.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void find_by_name_success() {
        // given
        User user = User.builder()
            .nickname("yun01")
            .email("yun01@naver.com")
            .password("qwer1234")
            .build();

        User savedUser = userRepository.save(user);

        Board board = Board.builder()
            .name("boardName01")
            .isPublic(true)
            .creator(savedUser)
            .build();

        boardRepository.save(board);

        // when
        Board findBoard = boardRepository.findByName("boardName01").orElseThrow();

        // then
        assertThat(findBoard.getName()).isEqualTo(board.getName());
        assertThat(findBoard.isPublic()).isTrue();
        assertThat(findBoard.getCreator().getEmail()).isEqualTo("yun01@naver.com");
    }
}