package com.example.newspeed.user.dto.request;

import lombok.Getter;

@Getter
public class DeletePassword {
    private final String password;

    public DeletePassword(String password) {
        this.password = password;
    }
}
