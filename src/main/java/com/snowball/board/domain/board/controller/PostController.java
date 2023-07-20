package com.snowball.board.domain.board.controller;

import com.snowball.board.domain.board.dto.PostDto;
import com.snowball.board.domain.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 종류	        URI	                                        method
 * 게시글 조회	/api/posts	                                GET
 * 게시글 상세    /api/posts/{postId}                         GET  -- 2023-07-16 추가
 * 게시글 작성	/api/posts	                                POST
 * 게시글 수정	/api/posts/{postId}	                        PUT
 * 게시글 삭제	/api/posts/{postId}	                        DELETE
 * 게시글 신고	/admin/reported-board	                    POST
 * 댓글 작성	    /api/posts/{postId}/comments	            POST
 * 댓글 수정	    /api/posts/{postId}/comments/{commentId}	PUT
 * 댓글 삭제	    /api/posts/{postId}/comments/{commentId}	DELETE
 * 댓글 신고	    /admin/reported-board	                    POST
 */


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postsService;

    @GetMapping
    public ResponseEntity<Page<PostDto>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "6") int size) {
        Page<PostDto> posts = postsService.getPostsByPage(page, size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        System.out.println("postId : " + postId);
        PostDto post = postsService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto createdPost = postsService.createPost(postDto);
        return ResponseEntity.ok(createdPost);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {
        PostDto updatedPost = postsService.updatePost(postId, postDto);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postsService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}