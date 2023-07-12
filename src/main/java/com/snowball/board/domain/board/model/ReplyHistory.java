package com.snowball.board.domain.board.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class ReplyHistory {

    private Long id;
    private Long commentId;
    private Long postId;
    private Long userId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String stateCode;
}

