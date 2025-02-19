package com.example.newspeed.friend.service;

import com.example.newspeed.friend.dto.FriendResponseDto;
import com.example.newspeed.friend.entity.Friend;
import com.example.newspeed.friend.repository.FriendRepository;
import com.example.newspeed.friend.status.FriendStatus;
import com.example.newspeed.user.dto.response.UserResponse;
import com.example.newspeed.user.entity.User;
import com.example.newspeed.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendResponseDto sendFriendRequest(Long requesterId, Long receiverId) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 아이디입니다"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 아이디입니다"));
        if (friendRepository.findByRequesterIdAndReceiverId(requesterId, receiverId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 친구 요청을 보냈거나 친구입니다.");
        }
        Friend friend = new Friend(requester, receiver, FriendStatus.PENDING);
        Friend savedFriend = friendRepository.save(friend);
        return FriendResponseDto.toDto(savedFriend);
    }

    public FriendResponseDto acceptFriendRequest(Long requesterId) {
        Friend friend = friendRepository.findById(requesterId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 친구 요청입니다"));
        friend.accept();
        Friend savedFriend = friendRepository.save(friend);
        return FriendResponseDto.toDto(savedFriend);
    }

    public void removeFriend(Long requesterId, Long receiverId) {
        Friend friend = friendRepository.findByRequesterIdAndReceiverId(requesterId, receiverId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 친구 관계입니다"));
        friendRepository.delete(friend);
    }

    public List<UserResponse> friendList(Long userId){
        List<Friend> friends = friendRepository.findAcceptedFriend(userId);

        return friends.stream().map(f -> new UserResponse(f.getRequester().getId().equals(userId) ? f.getReceiver() : f.getRequester()))
                .collect(Collectors.toList());
    }
}
