package com.github.noticeboardpractice.web.controller;

import com.github.noticeboardpractice.service.AuthService;
import com.github.noticeboardpractice.web.dto.auth.Login;
import com.github.noticeboardpractice.web.dto.auth.SignUp;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sign")
public class SingController {

    private final AuthService authService;

    @PostMapping("/register")
    public String registerUser(@RequestBody SignUp signUpRequest) {
        boolean isSuccess = authService.SignUp(signUpRequest);
        return isSuccess ? "회원가입에 성공 하였습니다." : "회원가입에 실패 하였습니다.";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody Login loginRequest, HttpServletResponse httpServletResponse) {
        String token = authService.login(loginRequest);
        httpServletResponse.setHeader("X-AUTH-TOKEN", token);
        return "로그인이 성공하였습니다.";
    }

}
