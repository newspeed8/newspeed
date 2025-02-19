package com.example.newspeed.friend.repository;

import com.example.newspeed.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByRequesterIdAndReceiverId(Long requesterId, Long receiverId);

        //특정 유저의 친구 목록 조회 (requester 또는 receiver가 해당 유저인 경우)
        @Query("""
        SELECT f FROM Friend f
        WHERE (f.requester.id = :userId OR f.receiver.id = :userId)
        AND f.status = 'ACCEPTED'
        """)
        List<Friend> findAcceptedFriend(@Param("userId") Long userId);
}
