package com.snowball.board.domain.board.dto;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CommentDto {
    private Long id;
    private Optional<Long> userId;
    private Long postId;
    private String content;
    private String createdAt;
    private String updatedAt;

    public void setUserId(Long id) {
    }
}