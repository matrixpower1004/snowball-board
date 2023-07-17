package com.snowball.board.common.util;

public enum UserRole {
    BLACK("블랙리스트"),
    BEGINNER("새싹회원"),
    EXPERT("우수회원"),
    ADMIN("관리자"),
    USER("일반회원"); // USER 상수 추가

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
