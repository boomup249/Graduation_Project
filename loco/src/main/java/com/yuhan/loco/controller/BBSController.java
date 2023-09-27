package com.yuhan.loco.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuhan.loco.bbs.PostDB;
import com.yuhan.loco.bbs.PostDTO;
import com.yuhan.loco.bbs.PostService;
import com.yuhan.loco.prefer.PreferService;
import com.yuhan.loco.user.UserDB;
import com.yuhan.loco.user.UserDTO;
import com.yuhan.loco.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

import java.util.ArrayList;
import java.util.List;

//게시판 컨트롤러

@Controller
public class BBSController {
	String page = "";
	private final PostService postService;
	private final UserService userService;
	public BBSController(PostService postService, UserService userService) {
		this.postService = postService;
		this.userService = userService;
	}

	@GetMapping("/post")
	public String post(Model model) 
	{	
		return "/post/list";
	}
	@GetMapping("/bbs_write")
	public String write(Model model, HttpServletRequest req) {
		String userId;
		HttpSession session = req.getSession(false);

		if(session != null) {
			userId = (String)session.getAttribute("user");
			UserDB userdb = userService.findUser(userId);
			model.addAttribute("userDTO", userdb);
			model.addAttribute("postDTO", new PostDB());
			return "/board/bbs_write";
		}
		return "/post/list";
	}

	@GetMapping("/bbs")
	public String bbs() 
	{		
		page = "bbs";
		return "/board/bbs";
	}	
	@PostMapping("post_write")
	public String write(PostDTO postDTO, UserDB userDB, Model model, HttpServletRequest req) {
		String userId;
		HttpSession session = req.getSession(false);
		userId = (String)session.getAttribute("user");
		if(page == "bbs") {
			postDTO.setCategory("bbs");
			postDTO.setWriter(userId);
			postDTO.setComment("");//임시, 댓글 구현 시 삭제
		}
		postService.create(postDTO.getCategory(), postDTO.getTitle(), postDTO.getWriter(), postDTO.getContent(), postDTO.getComment());
		return "/post/list";
	}
	@GetMapping("post_cancel")
	public String cancel() {
		return "/post/list";
	}
}