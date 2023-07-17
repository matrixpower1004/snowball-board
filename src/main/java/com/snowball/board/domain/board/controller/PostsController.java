package com.snowball.board.domain.board.controller;

import com.snowball.board.domain.board.dto.PostsDto;
import com.snowball.board.domain.board.service.PostsService;
import lombok.RequiredArgsConstructor;
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
public class PostsController {
    private final PostsService postsService;

    @GetMapping
    public ResponseEntity<List<PostsDto>> getAllPosts() {
        List<PostsDto> posts = postsService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostsDto> getPostById(@PathVariable Long postId) {
        PostsDto post = postsService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<PostsDto> createPost(@RequestBody PostsDto postDto) {
        PostsDto createdPost = postsService.createPost(postDto);
        return ResponseEntity.ok(createdPost);
    }

   @PutMapping("/{postId}")
   public ResponseEntity<PostsDto> updatePost(@PathVariable Long postId, @RequestBody PostsDto postDto) {
       PostsDto updatedPost = postsService.updatePost(postId, postDto);
       return ResponseEntity.ok(updatedPost);
   }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postsService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}