package com.example.newspeed.like.controller;

import com.example.newspeed.like.dto.response.LikeResponseDto;
import com.example.newspeed.like.service.LikeService;
import com.example.newspeed.common.consts.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<LikeResponseDto> likePost(
            HttpServletRequest request,
            @PathVariable Long postId
    ) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
            return ResponseEntity.status(401).body(null);
        }

        Long userId = (Long) session.getAttribute(Const.LOGIN_USER);
        LikeResponseDto response = likeService.togglePostLike(userId, postId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<LikeResponseDto> likeComment(
            HttpServletRequest request,
            @PathVariable Long commentId
    ) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
            return ResponseEntity.status(401).body(null);
        }

        Long userId = (Long) session.getAttribute(Const.LOGIN_USER);
        LikeResponseDto response = likeService.toggleCommentLike(userId, commentId);
        return ResponseEntity.ok(response);
    }
}
