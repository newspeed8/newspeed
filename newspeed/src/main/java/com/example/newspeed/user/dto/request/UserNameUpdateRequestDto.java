package com.example.newspeed.user.dto.request;

import lombok.Getter;

@Getter
public class UserNameUpdateRequestDto {
    private String newUserName;

    public UserNameUpdateRequestDto(String newUserName) {
        this.newUserName = newUserName;
    }
}
