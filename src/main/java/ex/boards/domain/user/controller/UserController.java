package ex.boards.domain.user.controller;

import ex.boards.domain.user.dto.SignupReqDto;
import ex.boards.domain.user.dto.SignupResDto;
import ex.boards.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResDto> signup(@RequestBody SignupReqDto request) {
        SignupResDto response = userService.signup(request);

        return ResponseEntity.ok(response);
    }
}
