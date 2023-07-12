package com.snowball.board.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    BLACK("블랙리스트"),
    BEGINNER("새싹회원"),
    EXPERT("우수회원"),
    ADMIN("관리자");

    private String value;

}