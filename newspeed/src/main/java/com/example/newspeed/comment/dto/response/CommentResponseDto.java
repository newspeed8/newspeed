package com.example.newspeed.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final Long userId;
    private final Long postId;
    private final String content;
    @JsonInclude(JsonInclude.Include.NON_NULL)//null 값이 아닌 경우에만 반환
    private Integer likeCount;

    public CommentResponseDto(Long id, Long userId, Long postId, String content) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }

    public CommentResponseDto(Long id, Long userId, Long postId, String content, Integer likeCount) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.likeCount = likeCount;
    }
}
