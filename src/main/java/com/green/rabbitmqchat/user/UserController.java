package com.green.rabbitmqchat.user;


import com.green.rabbitmqchat.common.ResVo;
import com.green.rabbitmqchat.user.model.UserSigninDto;
import com.green.rabbitmqchat.user.model.UserSigninVo;
import com.green.rabbitmqchat.user.model.UserSignupDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입 처리")
    public ResVo postSignup(@RequestBody UserSignupDto dto) {
        log.info("dto: {}", dto);
        return service.signup(dto);
    }

    @PostMapping("/signin")
    @Operation(summary = "인증", description = "아이디/비번을 활용한 인증처리")
    public UserSigninVo postSignin(HttpServletResponse res
                                , @RequestBody UserSigninDto dto) {
        log.info("dto: {}", dto);
        return service.signin(res, dto);  //result - 1: 성공, 2: 아이디 없음, 3: 비밀번호 틀림
    }

    @PostMapping("/signout")
    public ResVo postSignout(HttpServletResponse res) {
        return service.signout(res);
    }

    @GetMapping("/refresh-token")
    public UserSigninVo getRefreshToken(HttpServletRequest req) {
        return service.getRefreshToken(req);
    }
}
