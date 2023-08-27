package com.yuhan.loco.controller;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuhan.loco.user.UserDB;
import com.yuhan.loco.user.UserDTO;
import com.yuhan.loco.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

//로그인 과정 관련 컨트롤러

@Controller
public class LoginController {
	private final UserService userService;
	
	public LoginController(UserService userService) {
        this.userService = userService;
    }
	private boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return false;
		}
		return authentication.isAuthenticated();
	}
	//연결
	
	//login
	@GetMapping("/login") //로그인 페이지
	public String loginPage(UserDTO userDTO, @RequestParam(value="ad", defaultValue = "") String email, Model model) {
		//email 페이지에서 넘어온 경우: ad param은 이메일에서 넘어올 때 들어감, 디폴트를 null로 둬서 그냥 들어가도 오류 안남
		userDTO.setUserId(email);
		
		return "/login";
	}
	
	@PostMapping("/login") //로그인 처리
	public String login(BindingResult bindingResult, Model model) {
		//아이디 or 이메일 입력 필드는 userdto의 userId 활용 -> userEmail을 사용하면 제약 걸림(@)
		
		//입력 칸을 채우지 않았을 경우
		/* 어차피 html에서 required로 막혀서 보류
		if(userDTO.getUserId() == null) { //아이디 or 이메일이 null
			bindingResult.rejectValue("userId", "idIsNull", "이메일이나 아이디값을 입력해주세요.");
		}
		if(userDTO.getUserPwd() == null) { //비번 null
			bindingResult.rejectValue("userPwd", "PwdIsNull", "비밀번호를 입력해주세요.");
		}
		*/

		if(isAuthenticated()) {
			System.out.println("로그인 성공");
			return "/login";		
		}
		System.out.println("로그인 실패");
		bindingResult.rejectValue("userPwd", "UserIsNotExist", "아이디 혹은 비밀번호가 잘못되었습니다."); //정확히 pwd에 일어난 에러는 아니지만 위치상 여기에 처리, Security 적용하면서 작동 안함
		return "/login";
		/*비밀번호 암호화, 세션 부분 시큐리티로 대체 완료.
		 * 로그인은 잘 되는 것 같은데 메인 페이지에 오류 있음. 자세한건 메인 부분 참고 바람
		 * 로그인 실패 시 표시되던 오류 화면 전부 작동 안함. 확인 필요*/	
		}
		
	}
	
	

	
	
