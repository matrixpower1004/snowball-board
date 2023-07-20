package com.snowball.board.domain.board.mapper;


import com.snowball.board.domain.board.dto.PostDto;
import com.snowball.board.domain.board.model.Post;
import com.snowball.board.domain.user.model.User;
import com.snowball.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class PostsMapper {
    public PostDto mapToDto(Post post) {
        User user = post.getUser();
        String nickName = user != null ? user.getNickName() : null;

        return PostDto.builder()
                .id(post.getId())
                .userId(post.getUser().getId())
                .userRole(post.getUser().getUserRole())
                .title(post.getTitle())
                .content(post.getContent())
                .blindState(post.isBlindState())
                .nickName(nickName)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public Post mapToEntity(PostDto postDto) {
        return Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .blindState(postDto.isBlindState())
                .build();
    }
}