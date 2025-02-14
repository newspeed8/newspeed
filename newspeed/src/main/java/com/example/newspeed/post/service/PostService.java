package com.example.newspeed.post.service;

import com.example.newspeed.post.dto.request.PostRequest;
import com.example.newspeed.post.dto.response.PostResponse;
import com.example.newspeed.post.entity.Post;
import com.example.newspeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return mapToResponse(post);
    }

    @Transactional
    public PostResponse createPost(PostRequest request) {
        Post post = new Post(request.title(), request.content(), request.imageUrl(), request.nickname2());
        Post savedPost = postRepository.save(post);
        return mapToResponse(savedPost);
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다"));
        post.update(request.title(), request.content(), request.imageUrl(), request.nickname2());
        return mapToResponse(post);

        /*
        // 필드별 직접 변경
        post.setTitle(request.title());
        post.setContent(request.content());
        post.setImageUrl(request.imageUrl());
        post.setNickname2(request.nickname2());
        //이런식으로 필드별 직접 변경이 가능하나, 그냥 메서드로 빼내서 활용했음.
        */
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        postRepository.delete(post);
    }

    private PostResponse mapToResponse(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getImageUrl(),
                post.getNickname2(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
