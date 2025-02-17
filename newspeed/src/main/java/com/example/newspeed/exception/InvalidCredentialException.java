package com.example.newspeed.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialException extends ApplicationException {

    public InvalidCredentialException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
