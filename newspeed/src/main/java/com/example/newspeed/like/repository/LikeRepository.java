package com.example.newspeed.like.repository;

import com.example.newspeed.comment.entity.Comment;
import com.example.newspeed.like.entity.Like;
import com.example.newspeed.user.entity.User;
import com.example.newspeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndPost(User user, Post post);
    Optional<Like> findByUserAndComment(User user, Comment comment);

    int countByPost(Post post); // 좋아요 개수 조회
    int countByComment(Comment comment); // 댓글 좋아요 개수 조회
}

