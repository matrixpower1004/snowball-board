package com.snowball.board.domain.board.service.impl;

import com.snowball.board.domain.board.dto.ReplyDto;
import com.snowball.board.domain.board.model.Comment;
import com.snowball.board.domain.board.model.Reply;
import com.snowball.board.domain.board.repository.CommentRepository;
import com.snowball.board.domain.board.repository.ReplyRepository;
import com.snowball.board.domain.user.model.User;
import com.snowball.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReplyDto createReply(Long commentId, String content, Long userId) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            return null;
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }

        Reply reply = Reply.builder()
                .comment(comment)
                .user(user)
                .content(content)
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();

        Reply savedReply = replyRepository.save(reply);
        return mapToDTO(savedReply);
    }

    @Transactional
    public ReplyDto updateReply(Long replyId, String content) {
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

    public List<ReplyDto> getAllRepliesByCommentId(Long commentId) {
        List<Reply> replies = replyRepository.findAllByCommentId(commentId);
        return replies.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ReplyDto mapToDTO(Reply reply) {
        ReplyDto replyDTO = new ReplyDto();
        replyDTO.setId(reply.getId());
        if(reply.getUser() != null) {
            replyDTO.setUserId(reply.getUser().getId());
        }
        replyDTO.setCommentId(reply.getComment().getId());
        replyDTO.setContent(reply.getContent());
        replyDTO.setCreatedAt(reply.getCreatedAt().toString());
        replyDTO.setUpdatedAt(reply.getUpdatedAt().toString());
        return replyDTO;
    }
}