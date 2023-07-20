package com.snowball.board.domain.board.service.impl;

import com.snowball.board.domain.board.model.Post;
import com.snowball.board.domain.board.repository.PostRepository;
import com.snowball.board.domain.board.dto.PostDto;
import com.snowball.board.domain.board.mapper.PostsMapper;
import com.snowball.board.domain.user.repository.UserRepository;
import com.snowball.board.common.exception.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements com.snowball.board.domain.board.service.PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostsMapper postsMapper;
    @Transactional
    @Override
    public Page<PostDto> getPostsByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(postsMapper::mapToDto);
    }
    @Transactional
    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다. ID: " + postId));
        return postsMapper.mapToDto(post);
    }

    @Transactional
    @Override
    public PostDto createPost(PostDto postsDto) {
        Post post = postsMapper.mapToEntity(postsDto);
        Post createdPost = postRepository.save(post);
        return postsMapper.mapToDto(createdPost);
    }

    @Transactional
    @Override
    public PostDto updatePost(Long postId, PostDto postDto) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다. ID: " + postId));
        existingPost.setTitle(postDto.getTitle());
        existingPost.setContent(postDto.getContent());
        existingPost.setBlindState(postDto.isBlindState());
        Post updatedPost = postRepository.save(existingPost);
        return postsMapper.mapToDto(updatedPost);
    }

    @Transactional
    @Override
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new NotFoundException("게시물을 찾을 수 없습니다. ID: " + postId);
        }
        postRepository.deleteById(postId);
    }
}