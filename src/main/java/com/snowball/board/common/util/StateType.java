package com.snowball.board.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StateType {
    HIDE("블라인드"),
    DELETE("삭제"),
    SHOW("해제");

    private String value;

}
