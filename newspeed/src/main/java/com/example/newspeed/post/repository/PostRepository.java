package com.example.newspeed.post.repository;

import com.example.newspeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
    SELECT p FROM Post p
    JOIN Friend f ON p.user.id = f.requester.id
    WHERE p.user.id = :uerId
    AND f.status = 'ACCEPTED'
    ORDER BY p.createdAt DESC
    """)
    List<Post> findFriendPostsByUserId(@Param("userId") Long userId);
    // 유저 ID로 모든 게시물을 조회하는 메소드
    List<Post> findByUserId_Id(Long userId);
}
