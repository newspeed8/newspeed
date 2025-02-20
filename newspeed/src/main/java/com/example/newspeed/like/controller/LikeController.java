package com.example.newspeed.like.controller;

import com.example.newspeed.like.dto.response.LikeResponseDto;
import com.example.newspeed.like.service.LikeService;
import com.example.newspeed.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;
    private final JwtUtil jwtUtil;

    @PostMapping("/post/{postId}")
    public ResponseEntity<LikeResponseDto> likePost(
            HttpServletRequest request,
            @PathVariable Long postId
    ) {
        String token = jwtUtil.parseJwt(request); // JWT 토큰을 헤더에서 추출
        if (token == null || !jwtUtil.validateJwtToken(token)) {
            return ResponseEntity.status(401).body(null); // 토큰이 유효하지 않거나 없으면 401 반환
        }

        Long userId = jwtUtil.getUserIdFromJwtToken(token); // JWT에서 userId 추출
        LikeResponseDto response = likeService.togglePostLike(userId, postId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<LikeResponseDto> likeComment(
            HttpServletRequest request,
            @PathVariable Long commentId
    ) {
        String token = jwtUtil.parseJwt(request); // JWT 토큰을 헤더에서 추출
        if (token == null || !jwtUtil.validateJwtToken(token)) {
            return ResponseEntity.status(401).body(null); // 토큰이 유효하지 않거나 없으면 401 반환
        }

        Long userId = jwtUtil.getUserIdFromJwtToken(token); // JWT에서 userId 추출
        LikeResponseDto response = likeService.toggleCommentLike(userId, commentId);

        return ResponseEntity.ok(response);
    }
}
