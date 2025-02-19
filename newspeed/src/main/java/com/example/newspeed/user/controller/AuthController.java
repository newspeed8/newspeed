package com.example.newspeed.user.controller;

import com.example.newspeed.common.consts.Const;
import com.example.newspeed.common.jwt.JwtUtil;
import com.example.newspeed.user.dto.request.UserLoginRequestDto;
import com.example.newspeed.user.dto.response.JwtResponse;
import com.example.newspeed.user.dto.response.UserLoginResponseDto;
import com.example.newspeed.user.entity.User;
import com.example.newspeed.user.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserLoginRequestDto dto) {
        User user = authService.login(dto);
        String token = jwtUtil.generateJwtToken(user.getEmail());
        JwtResponse jwtResponse = new JwtResponse(token, user.getId(), user.getEmail());
        return ResponseEntity.ok(jwtResponse);
    }

        @PostMapping("/logout")
        public ResponseEntity<String> logout() {
            return ResponseEntity.ok("JWT 기반 로그아웃했습니다.");
        }


}
