package com.example.newspeed.post.controller;

import com.example.newspeed.post.dto.request.PostRequest;
import com.example.newspeed.post.dto.response.PostResponse;
import com.example.newspeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //뉴스피드 조회 (10개씩 페이징+생성일자 내림차순)
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(postService.getAllPosts(page));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(request));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long postId,
            @RequestParam Long userId, // 요청 파라미터에 userId 추가
            @RequestBody @Valid PostRequest request) {
        return ResponseEntity.ok(postService.updatePost(postId, userId, request));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @RequestParam Long userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }

    //유효성 검사 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("잘못된 요청입니다.");
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
