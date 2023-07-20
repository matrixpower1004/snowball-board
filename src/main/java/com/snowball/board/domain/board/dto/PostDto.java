package com.snowball.board.domain.board.dto;

import lombok.*;
import com.snowball.board.common.util.UserRole;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class PostDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private boolean blindState;
    private UserRole userRole;
    private String nickName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}