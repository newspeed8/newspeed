package com.example.newspeed.post.dto.response;

import java.time.LocalDateTime;

public record PostResponse(
        Long postId,
        String title,
        String content,
        String imageUrl,
        String nickname2,
        /*
        클라이언트한테 필요한 정보 전달을 위해 응답시점에 생성/수정 시점을 포함.
        즉, 클라이언트로 전달할 데이터 구조.
        이때 생성/수정 시간은 클라이언트가 이해하기 쉽게 포함하려고
         */
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}