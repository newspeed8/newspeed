package com.example.newspeed.profile.controller;

import com.example.newspeed.common.consts.Const;
import com.example.newspeed.post.dto.response.PostResponse;
import com.example.newspeed.profile.service.ProfileService;
import com.example.newspeed.user.dto.request.UserPasswordUpdateRequestDto;
import com.example.newspeed.user.dto.request.UserUserNameUpdateRequestDto;
import com.example.newspeed.user.dto.response.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    //유저 비밀번호 변경
    @PutMapping("/users/{id}/username")
    public ResponseEntity<UserResponseDto> updatePassword(
            @SessionAttribute(name = Const.LOGIN_USER) Long id,
            @RequestBody UserPasswordUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(profileService.updatePassword(id, dto.getOldPassword(), dto.getNewPassword()));
    }

    //유저 이름 변경
    @PutMapping("/users/{id}/password")
    public ResponseEntity<UserResponseDto> updateUserName(
            @SessionAttribute(name = Const.LOGIN_USER) Long id,
            @RequestBody UserUserNameUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(profileService.updateUserName(id, dto.getNewUserName()));
    }

    //유저 삭제
    @DeleteMapping("/users/{id}")
    public void deleteUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute(Const.LOGIN_USER);
        profileService.deleteUserById(id);
        session.invalidate();
    }

    // 유저 작성 게시물 전체 조회
    @GetMapping("/posts/{userId}")
    public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable Long userId){
        List<PostResponse> posts = profileService.getUserPosts(userId);
        return ResponseEntity.ok(posts);
    }

}
