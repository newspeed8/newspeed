package com.example.newspeed.like.dto.response;

import lombok.Getter;

@Getter
public class LikeResponseDto {
    private final Long postId;
    private final boolean liked; // true = 좋아요 추가됨, false = 좋아요 취소됨
    private final int likeCount;

    public LikeResponseDto(Long postId, boolean liked, int likeCount) {
        this.postId = postId;
        this.liked = liked;
        this.likeCount = likeCount;
    }
}

