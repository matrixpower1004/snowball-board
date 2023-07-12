package com.snowball.board.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ThreadType {
    POST("게시글"),
    COMMENT("댓글"),
    REPLY("답글");

    private String value;

}