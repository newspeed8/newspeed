package com.example.newspeed.post.repository;

import com.example.newspeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 유저 ID로 모든 게시물을 조회하는 메소드
    List<Post> findByUserId_Id(Long userId);
}
