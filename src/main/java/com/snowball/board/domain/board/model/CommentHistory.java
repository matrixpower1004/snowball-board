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
public class CommentHistory {
    private Long id;
    private Long userId;
    private Long postId;
    private Long commentId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String stateCode;
}
