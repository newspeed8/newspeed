package com.example.newspeed.comment.repository;

import com.example.newspeed.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Optional<Comment> findById(Long id);

    List<Comment> findByPost_PostId(Long postId);

    List<Comment> findByUserId(Long userId);
}
