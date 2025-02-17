package com.example.newspeed.comment.dto.response;

import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final Long userId;
    private final Long scheduleId;
    private final String content;

    public CommentResponseDto(Long id, Long userId, Long scheduleId, String content) {
        this.id = id;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.content = content;
    }
}
