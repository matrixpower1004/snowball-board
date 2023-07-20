package com.snowball.board.domain.board.dto;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ReplyDto {

    private Long id;
    private Long userId;
    private Long commentId;
    private String content;
    private String createdAt;
    private String updatedAt;
}