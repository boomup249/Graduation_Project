package com.yuhan.loco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.yuhan.loco.dto.UserDTO;

//회원가입 과정 관련 컨트롤러

/*필요한 것: 
 * 1. [select*.html 관련] 코드들 (예상)thymeleaf 이용해서 userDTO.get~() 하면 register에서 받아올 수 있게 만들기: 현재 코드는 타임리프 적용 가능할 경우 상정하고 짬
 * 
 * 	-> 앞 과정(pwd)은 라디오버튼, 콤보박스, 체크박스 등이 없어서 thymeleaf가 필드로 쉽게 적용되고 넘어감, 그래서 이 부분은 타임리프가 적용 가능한지 아직 확인 못함
 * 		-> 안될 경우 다른 방법으로 세션에 넣을 수 있도록
 * 
 * 	+) 체크박스 있는 폼은 3개 초과 체크되어 오면 오류 띄우고 못 넘어가게 막기
 * 
 * 2. [complete.html]때 세션에 있는 값 정리해서 db로 쏘기
 * 
 * 3. 가입 순서가 올바르게 적용되도록 유저가 임의로 주소 치고 들어올 경우를 막아야 함(ex. main에서 갑자기 /join_info 치고 들어오면 앞에 정보 못 받고 가입처리,
 * 		레지스터 안 거치고 /join_end치면 정보 session 생성 안됨)
 * 
 * 4. 스프링 시큐리티 활용(비밀번호 암호화 등)
 * 5. [join_pwd.html] 비밀번호 확인이랑 같은지 검증 + 세부 조건 걸기
 * 6. 로그인 관련
 * */

@Controller
public class JoinController {
	//전역
	String page = "";//현재 페이지 정보
	
	//레지스터: 정보가 있는 폼 -> 레지스터(중간 정보 세션에 넣은 후 다음 페이지로 연결) -> 다음 페이지 형태
	//form action 태그 참고
	@PostMapping("/register")
	public String makeSession(UserDTO userDTO, Model model, HttpSession session) {
		//userDTO에 값이 있을 경우 세션에 넣기: 개별로 if 둬서 페이지에 있는 거 다 넣기(한 페이지에 3개, 2개, 1개씩 있음)
		if((userDTO.getUserEmail()) != null) {
			session.setAttribute("userEmail", userDTO.getUserEmail());
		}
		if((userDTO.getUserPwd()) != null) {
			session.setAttribute("userPwd", userDTO.getUserPwd());
		}
		if((userDTO.getUserPwdck()) != null) {
			session.setAttribute("userPwdCk", userDTO.getUserPwdck());
		}
		if((userDTO.getUserBirth()) != null) {
			session.setAttribute("userBirth", userDTO.getUserBirth());
		}
		if((userDTO.getUserGender()) != null) {
			session.setAttribute("userGender", userDTO.getUserGender());
		}
		if((userDTO.getUserLike()) != null) {
			session.setAttribute("userLike", userDTO.getUserLike());
		}
		if((userDTO.getUserHate()) != null) {
			session.setAttribute("userHate", userDTO.getUserHate());
		}
		
		//페이지 이동
		switch (page) {
		case "pwd": { return "redirect:/join_info"; }
		case "info": { return "redirect:/join_like"; }
		case "like": { return "redirect:/join_hate"; }
		case "hate": { return "redirect:/join_end"; }
		default:
			throw new IllegalArgumentException("Unexpected value: " + page);
		}
	}
	
	//연결
	
	@GetMapping("/main")
	public String Main() {
		//page = "main";
		return "/main";
	}
	@PostMapping("/main") //아직 미정&미완성
	public String loginMain() {
		//page = "main";
		return "/main";
	}
	
	@GetMapping("join")
	public String sendEmail(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		//page = "email";
		return "/join/email";
	}
	
	//thymeleaf로 받아와서 post
	@PostMapping("join_pwd")
	//join_pwd는 이메일 값을 바로 받아서 화면에 띄움(중간저장 필요x) -> 타임리프로 대체
	public String getEmail(UserDTO userDTO, Model model) {
		System.out.println(userDTO.getUserEmail());
		page = "pwd";
		return "/join/join_pwd";
	}
	//info에서 이전 버튼으로 이동시 get -> 이 경우 이전에 넣은 정보 안 불러와짐
	@GetMapping("join_pwd")
	public String join_pwd(UserDTO userDTO, Model model) {
		page = "pwd";
		return "/join/join_pwd";
	}
	
	@GetMapping("join_info")
	public String join_info(UserDTO userDTO, Model model) {
		page = "info";
		return "/join/select_info";
	}
	
	@GetMapping("join_like")
	public String join_like(UserDTO userDTO, Model model) {
		page = "like";
		return "/join/select_like";
	}
	
	@GetMapping("join_hate")
	public String join_hate(UserDTO userDTO, Model model) {
		page = "hate";
		return "/join/select_hate";
	}
	
	@GetMapping("join_end")
	public String join_end(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if(session == null) {
			//세션이 위에서 안 만들어진 경우
			System.out.println("error_join_end");
		}
		
		System.out.println("------회원정보-------");
		System.out.println(session.getAttribute("userEmail"));
		System.out.println(session.getAttribute("userPwd"));
		System.out.println(session.getAttribute("userPwdCk"));
		
		session.invalidate(); //세션 소거
		page = "end";
		return "/join/complete";
	}
	
	@GetMapping("/login")
	public String login(UserDTO userDTO, Model model) {
		page = "login";
		return "/login";
	}
	
	
}
