package com.snowball.board.common.exception.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Auth")
public class InvalidAuthException extends RuntimeException{
    public InvalidAuthException(String message) {
        super(message);
    }
}
