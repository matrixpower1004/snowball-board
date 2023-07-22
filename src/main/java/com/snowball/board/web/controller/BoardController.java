package com.snowball.board.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//Test Controller for board
@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping
    public String showBoardList() {
        return "board";
    }

}
