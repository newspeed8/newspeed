package com.example.newspeed.like.dto.response;

import lombok.Getter;

@Getter
public class LikeResponseDto {
    private final Long targetId;
    private final boolean liked; // true = 좋아요 추가됨, false = 좋아요 취소됨
    private final int likeCount;

    public LikeResponseDto(Long targetId, boolean liked, int likeCount) {
        this.targetId = targetId;
        this.liked = liked;
        this.likeCount = likeCount;
    }
}

