package com.example.newspeed.friend.repository;

import com.example.newspeed.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

        // 삭제하는 유저와 친구인 사람 삭제(관계를 삭제)
        @Modifying
        @Query("""
        DELETE FROM Friend f WHERE (f.requester.id = :userId AND f.receiver.id = :friendId) OR (f.requester.id = :friendId AND f.receiver.id = :userId)
        """)
        void deleteByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);
}
