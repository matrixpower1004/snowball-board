package com.snowball.board.domain.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @GetMapping("/comments_and_replies")
    public String showCommentsAndRepliesPage() {
        return "comments_and_replies";
    }
}
