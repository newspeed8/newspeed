package com.example.newspeed.post.controller;

import com.example.newspeed.post.dto.request.PostRequest;
import com.example.newspeed.post.dto.response.PostResponse;
import com.example.newspeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(request));
    }

    //수정 시, PostRequest에 포함된 userId를 통해 작성자 검증
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable("postId") Long postId,
                                                   @RequestBody @Valid PostRequest request) {
        return ResponseEntity.ok(postService.updatePost(postId, request));
    }

    //삭제 시, URL 파라미터로 userId를 함께 전달 (예: /posts/1?userId=123)
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId,
                                           @RequestParam Long userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }

    //뉴스피드 조회: 생성일 내림차순 정렬, 페이지당 10개씩
    @GetMapping("/newsfeed")
    public ResponseEntity<Page<PostResponse>> getNewsfeed(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(postService.getNewsfeed(page));
    }

    //유효성 검사 실패 핸들러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("잘못된 요청입니다.");
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
