package com.snowball.board.domain.board.service;

import com.snowball.board.domain.board.dto.ReplyDTO;
import com.snowball.board.domain.board.emtity.Comment;
import com.snowball.board.domain.board.emtity.Reply;
import com.snowball.board.domain.board.emtity.User;
import com.snowball.board.domain.board.repository.CommentRepository;
import com.snowball.board.domain.board.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;

    public ReplyService(ReplyRepository replyRepository, CommentRepository commentRepository) {
        this.replyRepository = replyRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public ReplyDTO createReply(Long commentId, String content, Long userId) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            return null;
        }

        User user = new User();
        user.setId(userId);

        Reply reply = new Reply();
        reply.setComment(comment);
        reply.setUser(user);
        reply.setContent(content);
        reply.setCreatedAt(currentTime);
        reply.setUpdatedAt(currentTime);

        Reply savedReply = replyRepository.save(reply);
        return mapToDTO(savedReply);
    }

    @Transactional
    public ReplyDTO updateReply(Long replyId, String content) {
        Reply reply = replyRepository.findById(replyId).orElse(null);
        if (reply != null) {
            reply.setContent(content);
            reply.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Reply updatedReply = replyRepository.save(reply);
            return mapToDTO(updatedReply);
        }
        return null;
    }

    @Transactional
    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
    }

    public List<ReplyDTO> getAllRepliesByCommentId(Long commentId) {
        List<Reply> replies = replyRepository.findAllByCommentId(commentId);
        return replies.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ReplyDTO mapToDTO(Reply reply) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setId(reply.getId());
        replyDTO.setUserId(reply.getUser().getId());
        replyDTO.setCommentId(reply.getComment().getId());
        replyDTO.setContent(reply.getContent());
        replyDTO.setCreatedAt(reply.getCreatedAt().toString());
        replyDTO.setUpdatedAt(reply.getUpdatedAt().toString());
        return replyDTO;
    }
}
