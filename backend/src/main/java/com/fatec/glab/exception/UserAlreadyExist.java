package com.fatec.glab.exception;

public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist(String message) {
        super(message);
    }
}
