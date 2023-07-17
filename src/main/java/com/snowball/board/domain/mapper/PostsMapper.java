package com.snowball.board.domain.mapper;

import com.snowball.board.domain.board.model.Post;
import com.snowball.board.domain.board.dto.PostsDto;
import org.springframework.stereotype.Component;

@Component
public class PostsMapper {
    public PostsDto mapToDto(Post post) {
        return PostsDto.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .blindState(post.isBlindState())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public Post mapToEntity(PostsDto postDto) {
        return Post.builder()
                .userId(postDto.getUserId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .blindState(postDto.isBlindState())
                .build();
    }
}
