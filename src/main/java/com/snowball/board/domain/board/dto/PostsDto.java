package com.snowball.board.domain.board.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostsDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private boolean blindState;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}