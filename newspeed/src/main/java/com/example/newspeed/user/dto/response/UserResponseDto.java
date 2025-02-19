package com.example.newspeed.user.dto.response;

import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String userName;
    private final String email;

    public UserResponseDto(Long id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }


}
