package com.snowball.board.common.exception.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDto {
    private String message;
    private int statusCode;
}
