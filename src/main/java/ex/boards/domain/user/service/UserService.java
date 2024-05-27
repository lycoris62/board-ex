package ex.boards.domain.user.service;

import ex.boards.domain.user.dto.SignupReqDto;
import ex.boards.domain.user.dto.SignupResDto;
import ex.boards.domain.user.entity.User;
import ex.boards.domain.user.entity.UserRole;
import ex.boards.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public SignupResDto signup(SignupReqDto signupReqDto) {

        boolean existsByEmail = userRepository.existsByEmail(signupReqDto.getEmail());

        if (existsByEmail) {
            throw new IllegalArgumentException();
        }

        User user = User.builder()
            .nickname(signupReqDto.getNickname())
            .email(signupReqDto.getEmail())
            .password(passwordEncoder.encode(signupReqDto.getPassword()))
            .role(UserRole.USER)
            .build();

        User savedUser = userRepository.save(user);

        return new SignupResDto(savedUser);
    }
}
