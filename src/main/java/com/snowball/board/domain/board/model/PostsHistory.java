package com.snowball.board.domain.board.model;

import lombok.*;
// import com.snowball.board.common.util.UserRole;
import java.sql.Timestamp;
import com.snowball.board.common.util.StateType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class PostsHistory {

    private Long id;
    private Long userId;
    private Long postId;
    private String title;
    private String content;
//  private UserRole userRole;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private StateType stateCode;
}
