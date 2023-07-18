package com.yuhan.loco.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.yuhan.loco.game.GameDTO;
import com.yuhan.loco.game.GameService;
import com.yuhan.loco.user.UserDTO;
import com.yuhan.loco.user.UserService;

import jakarta.validation.Valid;

//회원가입 과정 관련 컨트롤러

/*설명:
 * - validation을 통해 UserDTO에 속성을 걸어서, BindingResult를 통해 에러를 받을 수 있음
 * 	이를 통해 에러 상황에 대처(ex. 비밀번호 확인값이 같지 않음, size 속성이 안 맞음)
 * 
 * - mysql 연결(pom.xml, application.properties 참고)
 * - UserDB 클래스는 @entity를 걸어서 repository.save(userDB)를 통해 db에 넣을 수 있게 함
 * 	@table(name = "test")를 통해 넣을 테이블을 지정함
 * 
 * 	참고) 프로퍼티의 spring.jpa.hibernate.ddl-auto 속성에 따라 jpa가 수행하는 방식이 달라짐
 * 		create: 무조건 새로 생성
 * 		update: 있으면 고쳐서 씀(이때 속성이 varchar(255) 등 최댓값으로 고정되므로, 직접 설정해 놓은 속성이 변경됨)
 * 		none: 안 고치고 찾아서 씀 -> 하지만 pk나 unique가 걸려 있는 항목이 테이블에 이미 존재할 경우에도 update로 대응함 -> "막을 방법 찾기"
 * 
 * - sevice에서 repository 받아와서 create 함수를 통해 userDTO 값을 userDB에 넣고, repository.save(userDB)함
 * 
 *  
 * */

/*To do: 
 * 1. [select*.html 관련] 코드들 (예상)thymeleaf 이용해서 userDTO.get~() 하면 register에서 받아올 수 있게 만들기: 현재 코드는 타임리프 적용 가능할 경우 상정하고 짬
 * 
 * 	-> 앞 과정(pwd)은 라디오버튼, 콤보박스, 체크박스 등이 없어서 thymeleaf가 필드로 쉽게 적용되고 넘어감, 그래서 이 부분은 타임리프가 적용 가능한지 아직 확인 못함
 * 		-> 안될 경우 다른 방법으로 세션에 넣을 수 있도록
 * 
 * 	+) 체크박스 선호 5개 이하로 막고, 선호에서 선택된 부분 비선호에서 선택 안되도록
 * 
 * <완료(email, pwd)> 2. [complete.html]때 thymeleaf userDTO 값 정리해서 db로 쏘기
 * 
 * <진행중>3. 가입 순서가 올바르게 적용되도록 유저가 임의로 주소 치고 들어올 경우를 막아야 함(ex. main에서 갑자기 /join_info 치고 들어오면 앞에 정보 못 받고 가입처리(예외(오류) 처리 -> 다시 메인으로),
 * 		/join_end 바로 치면 userDTO에 값이 없음)
 * 
 * 4. 스프링 시큐리티 활용(비밀번호 암호화 등)
 * <완료>5. [join_pwd.html] 비밀번호 확인이랑 같은지 검증 + 세부 조건 걸기
 * 6. 로그인 관련
 * */

@Controller
public class JoinController {
	//전역
	private final UserService userService;
	
	public JoinController(UserService userService) {
        this.userService = userService;
    }
	
	/*<가입 정보 처리 방법(최종)>
	 * 각 폼에 invisible로 이전 정보 칸을 만듦
	 * 	ex. select_info.html에 이메일과 비밀번호칸을 invisible로 만들어서 이전 값과 함께 select_like.html로 thymeleaf post로 넘김
	 * 마지막 complete.html까지 이런식으로 넘겨서 complete에서 정보 정리해서 db로 넘기기
	 */
	
	//연결
	//main
	@GetMapping("/main")
	public String Main() {
		return "/main";
	}
	@PostMapping("/main") //아직 미정&미완성
	public String loginMain() {
		return "/main";
	}
	
	//join_email
	@GetMapping("join")
	public String joinEmail(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		return "/join/email";
	}
	
	//※아래부터는 이전 값을 받아와야해서 post가 필요
	//join_pwd
	@PostMapping("join_pwd")
	public String joinPwd(UserDTO userDTO, Model model) {
		//join에서 받은 이메일은 이 페이지에서 수정 가능하므로 검증x + html로 required와 속성 지정해놓음
		System.out.println("------pwd------");
		System.out.println(userDTO.getUserEmail());
		return "/join/join_pwd";
	}
	@GetMapping("join_pwd")//오류로 막고 메인으로
	public String errorPwd(UserDTO userDTO, Model model) {
		return "/join/join_pwd";
	}
	
	//join_info
	@PostMapping("join_info")
	public String joinInfo(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {		
		//비밀번호가 틀리면
		if (!userDTO.getUserPwd().equals(userDTO.getUserPwdck())) {
			bindingResult.rejectValue("userPwdck", "passwordInCorrect", "패스워드가 일치하지 않습니다.");
			}
		
		//비밀번호가 15자리 초과하면
		if(userDTO.getUserPwd().length() > 15) {
			bindingResult.rejectValue("userPwd", "passwordLengthOver", "15자리를 초과하였습니다.");
		}
		
		//error
		if(bindingResult.hasErrors()) { return "/join/join_pwd"; }
		
		System.out.println("------info------");
		System.out.println(userDTO.getUserEmail());
		System.out.println(userDTO.getUserPwd());
		
		return "/join/select_info";
	}
	@GetMapping("join_info")//오류로 막고 메인으로
	public String errorInfo(UserDTO userDTO, Model model) {
		return "/join/select_info";
	}
	
	//join_like
	@PostMapping("join_like")
	public String joinLike(UserDTO userDTO, Model model) {
		System.out.println("------like------");
		System.out.println(userDTO.getUserEmail());
		System.out.println(userDTO.getUserPwd());
		return "/join/select_like";
	}
	@GetMapping("join_like")//오류로 막고 메인으로
	public String errorLike(UserDTO userDTO, Model model) {
		return "/join/select_like";
	}
	
	//join_hate
	@PostMapping("join_hate")
	public String joinHate(UserDTO userDTO, Model model) {
		System.out.println("------hate------");
		System.out.println(userDTO.getUserEmail());
		System.out.println(userDTO.getUserPwd());
		return "/join/select_hate";
	}
	@GetMapping("join_hate")//오류로 막고 메인으로
	public String errorHate(UserDTO userDTO, Model model) {
		return "/join/select_hate";
	}
	//
	
	
	//join_end
	@PostMapping("join_end")
	public String joinEnd(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
		System.out.println("------end------");
		System.out.println(userDTO.getUserEmail());
		System.out.println(userDTO.getUserPwd());
		
		//dto에 필수 값이 빠진 경우 메인으로 다시 보내기
		//error
		if(bindingResult.hasErrors()) {
			return "redirect:/main";
		}
		
		//dto값 db로 넘기기
		userService.create(userDTO.getUserEmail(), userDTO.getUserPwd());
		
		return "/join/complete";
	}
	@GetMapping("join_end")//오류로 막고 메인으로
	public String errorEnd(UserDTO userDTO, Model model) {
		return "/join/complete";
	}
	
	//login
	@GetMapping("/login")
	public String login(UserDTO userDTO, Model model) {
		return "/login";
	}
	
	
}
