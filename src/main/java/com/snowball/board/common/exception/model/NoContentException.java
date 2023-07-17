package com.snowball.board.common.exception.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "No Content")
public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
}

