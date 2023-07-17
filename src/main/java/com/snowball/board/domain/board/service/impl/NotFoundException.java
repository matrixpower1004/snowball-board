package com.snowball.board.domain.board.service.impl;


//fixme : common Exception 적용전 임시 클래스
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
