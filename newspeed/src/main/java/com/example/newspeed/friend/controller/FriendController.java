package com.example.newspeed.friend.controller;

import com.example.newspeed.friend.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;

    //친구 요청
    @PostMapping("/request")
    public ResponseEntity<Void> sendFriendRequest(@RequestParam Long requesterId, @RequestParam Long receiverId) {
        friendService.sendFriendRequest(requesterId, receiverId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //친구 수락
    @PostMapping("/accept")
    public ResponseEntity<Void> acceptFriendRequest(@RequestParam Long requesterId) {
        friendService.acceptFriendRequest(requesterId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    //친구 삭제
    @PostMapping("/remove")
    public ResponseEntity<Void> removeFriend(@RequestParam Long requesterId, @RequestParam Long receiverId) {
        friendService.removeFriend(requesterId, receiverId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
