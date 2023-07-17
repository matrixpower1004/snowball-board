package com.snowball.board.domain.board.controller;

import com.snowball.board.domain.board.dto.ReplyDTO;
import com.snowball.board.domain.board.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/comment/{commentId}/reply")
    public ResponseEntity<ReplyDTO> createReply(@PathVariable Long commentId, @RequestBody String content) {
        Long userId = 1L;

        ReplyDTO createdReply = replyService.createReply(commentId, content, userId);
        if (createdReply != null) {
            return new ResponseEntity<>(createdReply, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/reply/{replyId}")
    public ResponseEntity<ReplyDTO> updateReply(@PathVariable Long replyId, @RequestBody String content) {
        ReplyDTO updatedReply = replyService.updateReply(replyId, content);
        if (updatedReply != null) {
            return new ResponseEntity<>(updatedReply, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/comment/{commentId}/replies")
    public ResponseEntity<List<ReplyDTO>> getAllRepliesByCommentId(@PathVariable Long commentId) {
        List<ReplyDTO> replies = replyService.getAllRepliesByCommentId(commentId);
        return new ResponseEntity<>(replies, HttpStatus.OK);
    }


}
