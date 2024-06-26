package ex.boards.domain.user.entity;

import ex.boards.domain.board.entity.Board;
import ex.boards.domain.model.BaseEntity;
import ex.boards.domain.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 최소 접근 제어자 사용
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 60) // BCrypt 길이는 60
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "creator")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Post> postList = new ArrayList<>();

    @Builder
    private User(String nickname, String email, String password, UserRole role) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
