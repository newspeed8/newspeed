package com.example.newspeed.profile.dto;

import lombok.Getter;

@Getter
public class ProfileUpdateDto {
    private String username;
    private String oldPassword;
    private String newPassword;

    public ProfileUpdateDto(String username, String oldPassword, String newPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
