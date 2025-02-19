package com.example.newspeed.friend.dto;

import com.example.newspeed.friend.entity.Friend;
import com.example.newspeed.friend.status.FriendStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendResponseDto {
    private Long requesterId;
    private Long receiverId;
    private FriendStatus status;

    public static FriendResponseDto toDto(Friend friend) {
        return new FriendResponseDto(friend.getReceiver().getId(), friend.getRequester().getId(), friend.getStatus());
    }
}
