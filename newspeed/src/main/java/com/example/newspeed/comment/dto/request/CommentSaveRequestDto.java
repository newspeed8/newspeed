package com.example.newspeed.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentSaveRequestDto {
    @NotBlank(message = "댓글 내용은 필수로 입력해야합니다.")
    private String content;
}
