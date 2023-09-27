package com.yuhan.loco.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuhan.loco.prefer.PreferService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

//게시판 컨트롤러

@Controller
public class bbsController {
	
	@GetMapping("/post")
	public String post() 
	{	
		return "/post/list";
	}
	@GetMapping("/bbs_write")
	public String write(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if(session != null) return "/board/bbs_write";
		return "/post/list";
	}
	
	@GetMapping("/bbs")
	public String bbs() 
	{	
		return "/board/bbs";
	}	
}
