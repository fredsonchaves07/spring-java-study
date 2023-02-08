package com.fredsonchaves07.controller;

import com.fredsonchaves07.domain.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostController {

    @GetMapping
    public String postPage(Model model) {
        Post post = new Post();
        post.setTitle("Hello Spring Boot");
        post.setDescription("Spring Boot");
        post.setBody("Spring Boot is Awesome");
        model.addAttribute("post", post);
        return "post";
    }
}
