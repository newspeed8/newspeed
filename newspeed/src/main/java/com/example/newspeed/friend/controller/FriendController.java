package com.example.newspeed.friend.controller;

import com.example.newspeed.friend.dto.FriendResponseDto;
import com.example.newspeed.friend.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;

    //친구 요청
    @PostMapping("/request")
    @ResponseBody
    public ResponseEntity<FriendResponseDto> sendFriendRequest(@RequestParam Long requesterId, @RequestParam Long receiverId) {
        return ResponseEntity.ok(friendService.sendFriendRequest(requesterId, receiverId));
    }

    //친구 수락
    @PostMapping("/accept")
    @ResponseBody
    public ResponseEntity<FriendResponseDto> acceptFriendRequest(@RequestParam Long requesterId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(friendService.acceptFriendRequest(requesterId));
    }

    //친구 삭제
    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<Void> removeFriend(@RequestParam Long requesterId, @RequestParam Long receiverId) {
        friendService.removeFriend(requesterId, receiverId);
        return ResponseEntity.noContent().build();
    }
}
