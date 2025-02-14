package com.example.newspeed.post.dto;

import jakarta.validation.constraints.NotBlank;

public record PostRequest (
    @NotBlank String title,
    @NotBlank String content,
    @NotBlank String imageUrl,
    @NotBlank String nickname2
){}