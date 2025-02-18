package com.example.newspeed.post.repository;

import com.example.newspeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


    //유저 아이디로 친구 게시물 조회
    @Query("""
    SELECT p FROM Post p
    JOIN Friend f ON p.user.id = f.requester.id
    WHERE p.user.id = :uerId
    AND f.status = 'ACCEPTED'
    ORDER BY p.createdAt DESC
    """)
    Page<Post> findFriendPostsByUserId(Pageable pageable, @Param("userId") Long userId);
    //수정일 기준 시작일과 종료일로 게시물 조회
    Page<Post> findAllByUpdatedAtBetweenOrderByUpdatedAt(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
    //좋아요 기준 게시물 조회
    @Query("""
    SELECT p
    FROM Post p
    LEFT JOIN Like l ON p.postId = l.post.postId
    GROUP BY p.postId
    ORDER BY COUNT(l) DESC, p.updatedAt DESC
    """)
    Page<Post> findAllByLikeCount(Pageable pageable);
    // 유저 ID로 모든 게시물을 조회하는 메소드
    List<Post> findByUserId(Long userId);
}
