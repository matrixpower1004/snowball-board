package com.snowball.board.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StateType {
    BLIND("블라인드"),
    DELETE("삭제"),
    DISSOLUTION("해체");

    private String value;

}
