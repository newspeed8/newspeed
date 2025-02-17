package com.example.newspeed.user.controller;

import com.example.newspeed.common.consts.Const;
import com.example.newspeed.user.dto.request.UserLoginRequestDto;
import com.example.newspeed.user.dto.response.UserLoginResponseDto;
import com.example.newspeed.user.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody UserLoginRequestDto dto,
            HttpServletRequest request
    ) {
        UserLoginResponseDto result = authService.login(dto);
        HttpSession session = request.getSession();
        session.setAttribute(Const.LOGIN_USER, result.getUserId());
        return ResponseEntity.ok("로그인 성공");
    }
}
