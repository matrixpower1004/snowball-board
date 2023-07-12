package com.snowball.board.domain.board.model;

import lombok.*;

import java.sql.Timestamp;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Comment {

    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
