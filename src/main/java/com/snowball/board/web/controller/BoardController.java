package com.snowball.board.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping
    public String showBoardList() {
        return "board_list";
    }

    @GetMapping("/details")
    public String showBoardDetails() {
        return "board_details";
    }
}
