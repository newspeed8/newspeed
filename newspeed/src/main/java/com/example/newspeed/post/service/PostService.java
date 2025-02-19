package com.example.newspeed.post.service;

import com.example.newspeed.post.dto.request.PostRequest;
import com.example.newspeed.post.dto.response.PostResponse;
import com.example.newspeed.post.entity.Post;
import com.example.newspeed.post.repository.PostRepository;
import com.example.newspeed.user.entity.User;
import com.example.newspeed.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;



    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<Post> findAllPosts(Pageable pageable, Long userId) {
        return postRepository.findFriendPostsByUserId(pageable, userId);
    }

    @Transactional(readOnly = true)
    public Page<Post> findAllPostsByStartAndEnd(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate) {
        return postRepository.findAllByUpdatedAtBetweenOrderByUpdatedAt(pageable, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public Page<Post> findAllPostsByLikes(Pageable pageable) {
        return postRepository.findAllByLikeCountOrderByLikeCountDescUpdatedAtDesc(pageable);
    }

    public PostResponse findPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        return mapToResponse(post);
    }

    @Transactional
    public PostResponse createPost(PostRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다. userId: " + request.userId()));

        Post post = new Post(
                request.title(),
                request.content(),
                request.imageUrl(),
                request.nickname2(),
                user
        );

        Post savedPost = postRepository.save(post);
        return mapToResponse(savedPost);
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        /*
        // 필드별 직접 변경
        post.setTitle(request.title());
        post.setContent(request.content());
        post.setImageUrl(request.imageUrl());
        post.setNickname2(request.nickname2());
        //이런식으로 필드별 직접 변경이 가능하나, 그냥 메서드로 빼내서 활용했음.
        */
        post.update(
                request.title(),
                request.content(),
                request.imageUrl(),
                request.nickname2()
        );
        return mapToResponse(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
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

    @Transactional(readOnly = true)
    public List<PostResponse> getPostByUserId(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

}
