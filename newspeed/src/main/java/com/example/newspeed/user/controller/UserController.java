package com.example.newspeed.user.controller;

import com.example.newspeed.user.dto.request.UserSaveRequestDto;
import com.example.newspeed.user.dto.response.UserResponse;
import com.example.newspeed.user.dto.response.UserResponseDto;
import com.example.newspeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //유저 저장
    @PostMapping("/users/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserSaveRequestDto dto) {
        return ResponseEntity.ok(userService.save(dto));
    }

    //유저 전체 조회
    @GetMapping("/users/findall")
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    //id를 통한 유저 단건 조회
    @GetMapping("/users/find/{id}")
    public ResponseEntity<UserResponse> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findOne(id));
    }
// 비밀번호 수정 / 유저 이름 변경/ 유저 삭제문 프로필 컨트롤러로 이동
}