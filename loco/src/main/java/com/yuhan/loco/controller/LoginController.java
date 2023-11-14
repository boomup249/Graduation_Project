package com.yuhan.loco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuhan.loco.profile.ProfileService;
import com.yuhan.loco.profile.profileDB;
import com.yuhan.loco.user.UserDTO;
import com.yuhan.loco.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

//로그인 과정 관련 컨트롤러

@Controller
public class LoginController {
	private final UserService userService;
	private final ProfileService profileService;

	public LoginController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

	//연결
	@GetMapping("/")
	public String index() {
		return "/main";
	}
	//login
	@GetMapping("/login") //로그인 페이지
	public String loginPage(UserDTO userDTO, @RequestParam(value="ad", defaultValue = "") String email, Model model) {
		//email 페이지에서 넘어온 경우: ad param은 이메일에서 넘어올 때 들어감, 디폴트를 null로 둬서 그냥 들어가도 오류 안남
		userDTO.setUserId(email);

		return "/login";
	}

	@PostMapping("/login") //로그인 처리
	public String login(@Valid UserDTO userDTO, BindingResult bindingResult, Model model, HttpSession session) {
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

		//디비에 값이 없을 경우
		boolean ck;
		ck = userService.existUser(userDTO.getUserId(), userDTO.getUserPwd());
		if(!ck) {
			System.out.println("로그인 실패");
			bindingResult.rejectValue("userPwd", "UserIsNotExist", "아이디 혹은 비밀번호가 잘못되었습니다."); //정확히 pwd에 일어난 에러는 아니지만 위치상 여기에 처리
		}

		//에러가 있으면 로그인 화면으로 넘기기
		if(bindingResult.hasErrors()) { return "/login"; }
		else { //에러가 없음 -> 디비에도 값이 있음
			//세션 생성(id or email)
			session.setAttribute("user", userDTO.getUserId());

			//id 찾기
			String uID = userService.findUserId(userDTO.getUserId());
			//id로 프로필 디비 찾기
			profileDB pDB = profileService.findUser(uID);

			//세션 생성(프로필 이미지 src)
			if(pDB.getIMG() != null) {
				String profile_img = profileService.convertByteToBase64(pDB.getIMG());
				session.setAttribute("userImg", profile_img);
			}

			//메인으로 리다이렉트
			System.out.println("로그인 성공");
			return "redirect:/main";
		}

	}


	//로그아웃
	@GetMapping("/logout") //로그인 페이지
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);

		if(session == null) {
			//세션이 생성되지 않고 로그아웃 -> 에러
		} else { //세션이 있음
			session.invalidate();
		}

		return "redirect:/main";
	}


}


