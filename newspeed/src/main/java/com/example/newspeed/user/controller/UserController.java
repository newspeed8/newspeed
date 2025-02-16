package com.example.newspeed.user.controller;

import com.example.newspeed.common.consts.Const;
import com.example.newspeed.user.dto.request.UserPasswordUpdateRequestDto;
import com.example.newspeed.user.dto.request.UserSaveRequestDto;
import com.example.newspeed.user.dto.request.UserUserNameUpdateRequestDto;
import com.example.newspeed.user.dto.response.UserResponseDto;
import com.example.newspeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserSaveRequestDto dto){
        return ResponseEntity.ok(userService.save(dto));
    }

    //유저 전체 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    //id를 통한 유저 단건 조회
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findOne(id));
    }

    // -------- 프로필 컨트롤러로 이동 --------
//    //비밀번호 변경
//    @PutMapping("/users/{id}/username")
//    public ResponseEntity<UserResponseDto> updatePassword(
//            @SessionAttribute(name = Const.LOGIN_USER) Long id,
//            @RequestBody UserPasswordUpdateRequestDto dto
//            ){
//        return ResponseEntity.ok(userService.updatePassword(id, dto.getOldPassword(), dto.getNewPassword()));
//    }
//
//    //유저 이름 변경
//    @PutMapping("/users/{id}/password")
//    public ResponseEntity<UserResponseDto> updateUserName(
//            @SessionAttribute(name = Const.LOGIN_USER) Long id,
//            @RequestBody UserUserNameUpdateRequestDto dto
//            ){
//        return ResponseEntity.ok(userService.updateUserName(id, dto.getNewUserName()));
//    }

//    //유저 삭제
//    @DeleteMapping("/users/{id}")
//    public void deleteUser(HttpServletRequest request){
//        HttpSession session = request.getSession(false);
//        Long id = (Long) session.getAttribute(Const.LOGIN_USER);
//        userService.deleteUserById(id);
//        session.invalidate();
//    }
}
