package com.yuhan.loco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.yuhan.loco.dto.UserDTO;

@Controller
public class LocoController {
	//전역
	String page = "";
	
	//연결
	@GetMapping("/main")
	public String Main() {
		page = "main";
		return "main";
	}
	
	@GetMapping("join")
	public String join0() {
		page = "email";
		return "join/email";
	}
	
	@PostMapping("join_pwd")
	public String join1() {
		page = "pwd";
		return "join/join_pwd";
	}
	
	@GetMapping("join_pwd")
	public String join1_1() {
		page = "pwd";
		return "join/join_pwd";
	}
	
	@PostMapping("join_info")
	public String join2() {
		page = "info";
		return "join/select_info";
	}
	
	@GetMapping("join_info")
	public String join2_1() {
		page = "info";
		return "join/select_info";
	}
	
	@GetMapping("join_like")
	public String join3() {
		page = "like";
		return "join/select_like";
	}
	
	@GetMapping("join_hate")
	public String join4() {
		page = "hate";
		return "join/select_hate";
	}
	
	@GetMapping("join_end")
	public String join_end() {
		page = "end";
		return "join/complete";
	}
	
	@GetMapping("/login")
	public String login() {
		page = "login";
		return "login";
	}
	
	
}
