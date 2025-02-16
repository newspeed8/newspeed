package com.example.newspeed.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostRequest (
    @NotBlank(message = "제목은 필수입니다.") String title,
    @NotBlank(message = "내용은 필수입니다.") String content,
    @NotBlank(message = "이미지 URL은 필수입니다.") String imageUrl,
    @NotBlank(message = "닉네임은 필수입니다.") String nickname2,
    @NotNull(message = "userId는 필수입니다.") Long userId
){}
