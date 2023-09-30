package com.yuhan.loco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yuhan.loco.user.UserDTO;

@Controller
public class bbsController {
	@GetMapping("/bbs")
	public String bbs() {
		return "/game/bbs";
		}
	@GetMapping("/post")
    public String listPosts(Model model) {
        return "/post/list";
    }
	
	@GetMapping("/bbs_notice")
	public String bbsnotice() {
		return "/game/bbs_notice";
	}
	
	@GetMapping("/bbs_write")
	public String bbswrite(Model model, UserDTO userDTO) {
		
		return "/game/bbs_write";
	}
}
