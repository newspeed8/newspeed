package com.example.newspeed.profile.controller;

import com.example.newspeed.friend.service.FriendService;
import com.example.newspeed.post.dto.response.PostResponse;
import com.example.newspeed.profile.service.ProfileService;
import com.example.newspeed.user.dto.request.DeletePassword;
import com.example.newspeed.user.dto.request.UserPasswordUpdateRequestDto;
import com.example.newspeed.user.dto.request.UserUserNameUpdateRequestDto;
import com.example.newspeed.user.dto.response.UserResponse;
import com.example.newspeed.user.dto.response.UserResponseDto;
import com.example.newspeed.user.entity.User;
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
    private final FriendService friendService;

    //유저 비밀번호 변경
    @PutMapping("/users/{id}/password")
    public ResponseEntity<UserResponse> updatePassword(
            @PathVariable("id") Long id,
            @RequestBody UserPasswordUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(profileService.updatePassword(id, dto.getOldPassword(), dto.getNewPassword()));
    }

    //유저 이름 변경
    @PutMapping("/users/{id}/username")
    public ResponseEntity<UserResponse> updateUserName(
            @PathVariable("id") Long id,
            @RequestBody UserUserNameUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(profileService.updateUserName(id, dto.getNewUserName()));
    }

    //유저 삭제
    @DeleteMapping("/users/{id}")
    public void deleteUser(
            @PathVariable("id") Long id,
            @RequestBody DeletePassword deleteDto
            ){
        profileService.deleteUserById(id, deleteDto);
    }

    // 유저 작성 게시물 전체 조회
    @GetMapping("/posts/{id}")
    public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable("id") Long id){
        List<PostResponse> posts = profileService.getUserPosts(id);
        return ResponseEntity.ok(posts);
    }

    // 친구 목록
    @GetMapping("/friends/{id}")
    public ResponseEntity<List<UserResponse>> friendList(@PathVariable Long id){
        List<UserResponse> friends = friendService.friendList(id);
        return ResponseEntity.ok(friends);
    }
}
