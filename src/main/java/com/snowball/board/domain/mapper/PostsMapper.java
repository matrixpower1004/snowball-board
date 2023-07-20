package com.snowball.board.domain.mapper;

import com.snowball.board.domain.board.model.Post;
import com.snowball.board.domain.board.dto.PostsDto;
import com.snowball.board.domain.board.service.impl.NotFoundException;
import com.snowball.board.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class PostsMapper {

    private final UserRepository userRepository;

    public PostsMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PostsDto mapToDto(Post post) {
        return PostsDto.builder()
                .id(post.getId())
                .userId(post.getUserId().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .blindState(post.isBlindState())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public Post mapToEntity(PostsDto postDto) {
        Post post = Post.builder() // 빌더 패턴을 사용하여 Post 객체 생성
                .userId(userRepository.findById(postDto.getUserId())
                        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다. ID: " + postDto.getUserId())))
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .blindState(postDto.isBlindState())
                .build();
        return post;
    }
}
