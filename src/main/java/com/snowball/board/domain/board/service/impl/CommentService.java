package com.snowball.board.domain.board.service.impl;

import com.snowball.board.common.exception.model.NotFoundException;
import com.snowball.board.domain.board.dto.CommentDto;
import com.snowball.board.domain.board.model.Comment;
import com.snowball.board.domain.board.model.Post;
import com.snowball.board.domain.board.model.Reply;
import com.snowball.board.domain.board.repository.CommentRepository;
import com.snowball.board.domain.board.repository.PostRepository;
import com.snowball.board.domain.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;


    @Transactional
    public CommentDto createComment(Long postId, String content) {
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
    public CommentDto updateComment(Long commentId, String content) {
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
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다. ID: " + commentId));

        List<Reply> replies = comment.getReplies();
        replyRepository.deleteAllByCommentId(commentId);


        commentRepository.delete(comment);
    }

    private CommentDto mapToDTO(Comment comment) {
        CommentDto commentDTO = new CommentDto();
        commentDTO.setId(comment.getId());
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setPostId(comment.getPost().getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedAt(comment.getCreatedAt().toString());
        commentDTO.setUpdatedAt(comment.getUpdatedAt().toString());
        return commentDTO;
    }

    public List<CommentDto> getAllCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto commentDto = new CommentDto();
            commentDto.setId(comment.getId());
            commentDto.setContent(comment.getContent());
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }
}