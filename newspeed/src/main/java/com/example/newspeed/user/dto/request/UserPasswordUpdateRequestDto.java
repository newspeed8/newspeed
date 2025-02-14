package com.example.newspeed.user.dto.request;

import lombok.Getter;

@Getter
public class UserPasswordUpdateRequestDto {
    private String oldPassword;
    private String newPassword;

    public UserPasswordUpdateRequestDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
