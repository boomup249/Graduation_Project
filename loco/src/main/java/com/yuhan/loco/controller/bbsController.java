package com.yuhan.loco.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuhan.loco.prefer.PreferService;

import java.util.ArrayList;
import java.util.List;

//게시판 컨트롤러

@Controller
public class BBSController {
	@GetMapping("/bbs")
	public String bbs() 
	{		
		return "/game/bbs";
	}	
}
