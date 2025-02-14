package com.example.newspeed.user.dto.request;

import lombok.Getter;

@Getter
public class UserUserNameUpdateRequestDto {
    private String newUserName;

    public UserUserNameUpdateRequestDto(String newUserName) {
        this.newUserName = newUserName;
    }
}
