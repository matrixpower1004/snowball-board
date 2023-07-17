package com.snowball.board.domain.board.service;


import com.snowball.board.domain.board.dto.PostsDto;

import java.util.List;

public interface PostsService {
    List<PostsDto> getAllPosts();
    PostsDto getPostById(Long postId);
    PostsDto createPost(PostsDto postsDto);
    PostsDto updatePost(Long postId, PostsDto postDto);
    void deletePost(Long postId);
}
