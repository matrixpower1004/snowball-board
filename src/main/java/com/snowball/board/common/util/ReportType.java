package com.snowball.board.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportType {
    ABUSE("욕설"),
    CALUMNY("비방"),
    LEWDNESS("음란"),
    SPAM("광고");

    private String value;

}