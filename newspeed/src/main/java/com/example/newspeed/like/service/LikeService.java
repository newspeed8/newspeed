package com.example.newspeed.like.service;

import com.example.newspeed.like.dto.response.LikeResponseDto;
import com.example.newspeed.like.entity.Like;
import com.example.newspeed.like.repository.LikeRepository;
import com.example.newspeed.post.entity.Post;
import com.example.newspeed.post.repository.PostRepository;
import com.example.newspeed.user.entity.User;
import com.example.newspeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public LikeResponseDto togglePostLike(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("게시물을 찾을 수 없습니다."));

        boolean liked;
        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            liked = false; // 좋아요 취소됨
        } else {
            likeRepository.save(new Like(user, post));
            liked = true; // 좋아요 추가됨
        }

        int likeCount = likeRepository.countByPost(post); // 좋아요 개수 가져오기
        return new LikeResponseDto(postId, liked, likeCount);
    }

    // 댓글
//    @Transactional
//    public void toggleCommentLike(Long userId, Long commentId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));
//
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new IllegalStateException("댓글을 찾을 수 없습니다."));
//
//        likeRepository.findByUserAndComment(user, comment).ifPresentOrElse(
//                likeRepository::delete,
//                () -> likeRepository.save(new Like(user, comment))
//        );
//    }
}
