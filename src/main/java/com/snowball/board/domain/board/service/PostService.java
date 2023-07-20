package com.snowball.board.domain.board.service;


import com.snowball.board.domain.board.dto.PostDto;
import org.springframework.data.domain.Page;

public interface PostService {
    Page<PostDto> getPostsByPage(int page, int size);
    PostDto getPostById(Long postId);
    PostDto createPost(PostDto postsDto);
    PostDto updatePost(Long postId, PostDto postDto);
    void deletePost(Long postId);
}