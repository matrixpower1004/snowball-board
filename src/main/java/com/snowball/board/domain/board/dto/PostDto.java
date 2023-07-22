package com.snowball.board.domain.board.dto;

import lombok.*;
import com.snowball.board.common.util.UserRole;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}