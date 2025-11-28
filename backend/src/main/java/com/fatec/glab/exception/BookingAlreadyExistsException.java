package com.fatec.glab.exception;

public class BookingAlreadyExistsException extends RuntimeException {

    public BookingAlreadyExistsException(String message) {
        super(message);
    }

}
