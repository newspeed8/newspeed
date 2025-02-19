package com.example.newspeed.comment.controller;

import com.example.newspeed.comment.dto.request.CommentSaveRequestDto;
import com.example.newspeed.comment.dto.request.CommentUpdateRequestDto;
import com.example.newspeed.comment.dto.response.CommentResponseDto;
import com.example.newspeed.comment.service.CommentService;
import com.example.newspeed.common.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final JwtUtil jwtUtil;
    //밑의 기능들은 로그인을 한 후에 실행 가능
    //댓글 작성
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> save(
            @RequestHeader("Authorization") String token,
            @PathVariable Long postId,
            @RequestBody @Valid CommentSaveRequestDto dto
            ){
        Long userId=jwtUtil.getUserIdFromJwtToken(token);
        return ResponseEntity.ok(commentService.save(userId, postId, dto));
    }

    //댓글 조회
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findByPost(@PathVariable Long postId){
        return ResponseEntity.ok(commentService.findByPost(postId));
    }

    //댓글 단건 조회
    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> findOne(@PathVariable Long id){
        return ResponseEntity.ok(commentService.findOne(id));
    }

    //댓글 수정
    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> update(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody CommentUpdateRequestDto dto
    ){
        Long userId=jwtUtil.getUserIdFromJwtToken(token);
        return ResponseEntity.ok(commentService.update(id, userId, dto));
    }

    //댓글 삭제
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> delete(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id){
        Long userId=jwtUtil.getUserIdFromJwtToken(token);
        commentService.delete(id, userId);
        return ResponseEntity.ok().build();
    }
}
