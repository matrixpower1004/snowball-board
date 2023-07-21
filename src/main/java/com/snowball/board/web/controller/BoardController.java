package com.snowball.board.web.controller;

import com.snowball.board.domain.board.dto.PostDto;
import com.snowball.board.domain.board.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final PostService postService;

    @Autowired
    public BoardController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String showBoardList(Model model) {
        List<PostDto> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "board_list";
    }
    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model) {
        PostDto post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "board_edit";
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

    @PostMapping("/create")
    public String createPost(@ModelAttribute PostDto postDto) {
        postService.createPost(postDto);
        return "redirect:/board";
    }
    @GetMapping("/write")
    public String showWriteBoardForm() {
        return "board_write";
    }


    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/board";
    }
    @PostMapping("/edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute PostDto postDto) {
        postService.updatePost(id, postDto);
        return "redirect:/board/" + id;
    }
    @DeleteMapping("/delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/board";
    }
}
