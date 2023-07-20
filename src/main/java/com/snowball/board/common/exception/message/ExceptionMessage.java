package com.snowball.board.common.exception.message;

public enum ExceptionMessage {
    USER_NOT_FOUND("해당 유저를 찾을 수 없습니다."),
    MISSING_REQUIRED_FIELD("필수 필드가 누락되었습니다."),
    INNER_SERVER_ERROR("서버 내부 오류가 발생했습니다."),
    ALREADY_EXISTS_USER("이미 존재하는 유저입니다."),
    DUPLICATE_NICKNAME("해당 닉네임은 이미 사용 중입니다."),
    DUPLICATE_EMAIL("해당 이메일은 이미 사용 중입니다.");
    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
