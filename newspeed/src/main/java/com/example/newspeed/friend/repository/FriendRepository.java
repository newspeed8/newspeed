package com.example.newspeed.friend.repository;

import com.example.newspeed.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByRequesterIdAndReceiverId(Long requesterId, Long receiverId);
}
