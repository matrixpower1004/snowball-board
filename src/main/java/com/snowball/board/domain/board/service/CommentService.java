package com.snowball.board.domain.board.service;

import com.snowball.board.domain.board.dto.CommentDTO;
import com.snowball.board.domain.board.emtity.Comment;
import com.snowball.board.domain.board.model.Post;
import com.snowball.board.domain.board.repository.CommentRepository;
import com.snowball.board.domain.board.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public CommentDTO createComment(Long postId, String content) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return null;
        }

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(content);
        comment.setCreatedAt(currentTime);
        comment.setUpdatedAt(currentTime);

        Comment savedComment = commentRepository.save(comment);
        return mapToDTO(savedComment);
    }

    @Transactional
    public CommentDTO updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            comment.setContent(content);
            comment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Comment updatedComment = commentRepository.save(comment);
            return mapToDTO(updatedComment);
        }
        return null;
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<CommentDTO> getAllCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setPostId(comment.getPost().getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedAt(comment.getCreatedAt().toString());
        commentDTO.setUpdatedAt(comment.getUpdatedAt().toString());
        return commentDTO;
    }
}
