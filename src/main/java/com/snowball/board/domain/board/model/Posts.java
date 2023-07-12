package com.snowball.board.domain.board.model;

import lombok.*;
// import com.snowball.board.common.util.UserRole;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Posts {

    private Long id;
    private Long userId;
    private String title;
    private String content;
    private boolean blindState;
    private UserRole userRole;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}

