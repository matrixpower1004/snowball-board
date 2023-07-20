package com.snowball.board.domain.board.service.impl;

import com.snowball.board.domain.board.model.Post;
import com.snowball.board.domain.board.dto.PostsDto;
import com.snowball.board.domain.board.repository.PostsRepository;
import com.snowball.board.domain.board.service.PostsService;
import com.snowball.board.domain.mapper.PostsMapper;
import com.snowball.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final PostsMapper postsMapper;

    @Override
    public List<PostsDto> getAllPosts() {
        List<Post> posts = postsRepository.findAll();
        return posts.stream()
                .map(postsMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostsDto getPostById(Long postId) {
        Post post = postsRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다. ID: " + postId));
        return postsMapper.mapToDto(post);
    }

    @Override
    public PostsDto createPost(PostsDto postsDto) {
        Post post = postsMapper.mapToEntity(postsDto);
        Post createdPost = postsRepository.save(post);
        return postsMapper.mapToDto(createdPost);
    }

    @Override
    public PostsDto updatePost(Long postId, PostsDto postDto) {
        Post existingPost = postsRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다. ID: " + postId));

        // 나머지 필드 업데이트
        existingPost.setTitle(postDto.getTitle());
        existingPost.setContent(postDto.getContent());
        existingPost.setBlindState(postDto.isBlindState());

        Post updatedPost = postsRepository.save(existingPost);
        return postsMapper.mapToDto(updatedPost);
    }

    @Override
    public void deletePost(Long postId) {
        if (!postsRepository.existsById(postId)) {
            throw new NotFoundException("게시물을 찾을 수 없습니다. ID: " + postId);
        }
        postsRepository.deleteById(postId);
    }
}
