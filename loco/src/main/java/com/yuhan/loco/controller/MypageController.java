package com.yuhan.loco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yuhan.loco.prefer.PreferService;
import com.yuhan.loco.user.UserDTO;
import com.yuhan.loco.user.UserService;

//마이페이지 컨트롤러

/*설명:
 * 
 * */

/*To do: 
 * 
 * */

@Controller
public class MypageController {
	//전역
	String page = "";
	private final UserService userService;
	private final PreferService preferService;
	
	public MypageController(UserService userService, PreferService preferService) {
        this.userService = userService;
        this.preferService = preferService;
    }
	
	
	//연결
	//join_modify < modify_info.html을 작동시키기 위한 코드(임의로 넣어놓은 거라 이후에 수정 부탁) >
	@GetMapping("join_modify")
	public String joinModify(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		page = "modify";
		return "/join/modify_info";
		}
	
	
}
