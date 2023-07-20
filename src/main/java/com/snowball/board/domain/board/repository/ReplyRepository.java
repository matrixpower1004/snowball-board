package com.snowball.board.domain.board.repository;

import com.snowball.board.domain.board.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByCommentId(Long commentId);
}