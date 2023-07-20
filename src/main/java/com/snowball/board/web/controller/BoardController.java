package com.snowball.board.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
public class BoardController {
    @GetMapping
    public String showBoardList() {
        return "board_list";
    }
    @GetMapping("/{postId}")
    public String showBoardDetails(@PathVariable Long postId,
                                   @RequestParam("title") String title,
                                   @RequestParam("nickName") String nickName,
                                   @RequestParam("createdAt") String createdAt,
                                   Model model) {
        model.addAttribute("postId", postId);
        model.addAttribute("title", title);
        model.addAttribute("nickName", nickName);
        model.addAttribute("createdAt", createdAt);
        return "board_detail";
    }

    @GetMapping("/write")
    public String showWriteBoardForm() {
        return "board_write";
    }
}