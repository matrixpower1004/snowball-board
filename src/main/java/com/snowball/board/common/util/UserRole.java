package com.snowball.board.common.util;

public enum UserRole {
    BLACK("블랙리스트"),
    BEGINNER("새싹회원"),
    EXPERT("우수회원"),
    ROLE_USER("일반"),
    ADMIN("관리자");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
