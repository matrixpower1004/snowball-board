package com.snowball.board.domain.board.controller;

import com.snowball.board.domain.board.dto.ReplyDto;
import com.snowball.board.domain.board.service.impl.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/comments/{commentId}/replies")
    public ResponseEntity<ReplyDto> createReply(@PathVariable Long commentId, @RequestBody String content) {
        Long userId = 1L;

        ReplyDto createdReply = replyService.createReply(commentId, content, userId);
        if (createdReply != null) {
            return new ResponseEntity<>(createdReply, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/replies/{replyId}")
    public ResponseEntity<ReplyDto> updateReply(@PathVariable Long replyId, @RequestBody String content) {
        ReplyDto updatedReply = replyService.updateReply(replyId, content);
        if (updatedReply != null) {
            return new ResponseEntity<>(updatedReply, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/comments/{commentId}/replies")
    public ResponseEntity<List<ReplyDto>> getAllRepliesByCommentId(@PathVariable Long commentId) {
        List<ReplyDto> replies = replyService.getAllRepliesByCommentId(commentId);
        return new ResponseEntity<>(replies, HttpStatus.OK);
    }
}