package com.snowball.board.domain.board.model;

import lombok.*;
import java.sql.Timestamp;
import com.snowball.board.common.util.StateType;
@Getter
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
    private StateType stateCode;
}

