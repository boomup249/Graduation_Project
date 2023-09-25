package com.yuhan.loco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;



import org.springframework.ui.Model;

@Controller
public class PostController {

    @GetMapping("/posts")
    public String listPosts(Model model) {
        return "post/list";
    }
    // 다른 요청에 대한 메서드도 추가 가능
}
