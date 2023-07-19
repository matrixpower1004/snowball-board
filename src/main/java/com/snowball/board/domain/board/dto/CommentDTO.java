package com.snowball.board.domain.board.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CommentDTO {

    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private String createdAt;
    private String updatedAt;
}
