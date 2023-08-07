package com.yuhan.loco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.yuhan.loco.user.UserDTO;
import com.yuhan.loco.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
 * <완료> 1. [select*.html 관련] 코드들 (예상)thymeleaf 이용해서 userDTO.get~() 하면 받아올 수 있게 만들기: 현재 코드는 타임리프 적용 가능할 경우 상정하고 짬
 * 
 * 	-> 앞 과정(pwd)은 라디오버튼, 콤보박스, 체크박스 등이 없어서 thymeleaf가 필드로 쉽게 적용되고 넘어감, 그래서 이 부분은 타임리프가 적용 가능한지 아직 확인 못함
 * 		-> 안될 경우 다른 방법으로 세션에 넣을 수 있도록
 * 
 * 	+) 체크박스 선호 5개 이하로 막고, 선호에서 선택된 부분 비선호에서 선택 안되도록
 * 
 * <완료> 2. [complete.html]때 thymeleaf userDTO 값 정리해서 db로 쏘기
 * 
 * <완료> 3. 그냥 get 페이지를 만들지 않으면 해결됨 -> post로 받으려면 순서를 지키게 되기 때문
 * 
 * 4. 스프링 시큐리티 활용(비밀번호 암호화 등)
 * <완료>5. [join_pwd.html] 비밀번호 확인이랑 같은지 검증 + 세부 조건 걸기
 * <진행중 (session 생성 완료)>6. 로그인 관련
 * */

@Controller
public class JoinController {
	//전역
	String page = "";
	private final UserService userService;
	
	public JoinController(UserService userService) {
        this.userService = userService;
    }
	
	/*<가입 정보 처리 방법(최종)>
	 * 각 폼에 invisible로 이전 정보 칸을 만듦
	 * 	ex. select_info.html에 이메일과 비밀번호칸을 invisible로 만들어서 이전 값과 함께 select_like.html로 thymeleaf post로 넘김
	 * 마지막 complete.html까지 이런식으로 넘겨서 complete에서 정보 정리해서 db로 넘기기
	 * 
	 * 이전 버튼과 다음 버튼 모두 post mapping, 이전과 다음 버튼을 구분하기 위해 page를 전역 변수로 사용하여 실행 코드를 다르게 줌
	 */
	
	//연결
	//main
	@GetMapping("/main")
	public String Main() {
		page = "main";
		return "/main";
	}
	
	//join_email
	@GetMapping("join")
	public String joinEmail(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		page = "email";
		return "/join/email";
	}
	
	//※아래부터는 이전 값을 받아와야해서 post가 필요
	//join_pwd
	@PostMapping("join_pwd")
	public String joinPwd(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
		//join에서 받은 이메일은 이 페이지에서 수정 가능하므로 검증x + html로 required와 속성 지정해놓음
		
		if(page.equals("info")) { //이전 버튼으로 넘어온 경우 초기화
			
			if(bindingResult.hasFieldErrors("userBirh")) { //info에서 birth 안 선택하고 넘기면 에러 발생
				userDTO.setUserBirth(null);
			}
			
			//이전 정보 일부 초기화
			userDTO.setUserBirth(null);
			userDTO.setUserGender(null);
			userDTO.setUserPwd(null); //pwd는 초기화하지 않으면 pwdck에서 에러 발생, + 지우고 입력 번거로움
		}
		
		page = "pwd";
		return "/join/join_pwd";
	}
	@GetMapping("join_pwd")
	public String getPwd(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
		page = "pwd";
		return "/join/join_pwd";
	}
	
	//join_info
	@PostMapping("join_info")
	public String joinInfo(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {	
		
		if(page.equals("pwd")) {
			
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
			
			
		}
		
		page = "info";
		
		return "/join/select_info";
	}
	
	//join_like
	@PostMapping("join_like")
	public String joinLike(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
		
		if(page.equals("info")) {
			//gender 미선택 후 넘김
			if(userDTO.getUserGender() == null) {
				bindingResult.rejectValue("userGender", "genderIsNull", "성별은 필수 선택입니다.");
			}
			
			//birth 미선택 후 넘기면 어차피 haserror 뜸 -> 형식 안 맞아서
			
			//error
			if(bindingResult.hasErrors()) { return "/join/select_info"; }
			
		}
		
		page = "like";
		return "/join/select_like";
	}
	
	//join_hate
	@PostMapping("join_hate")
	public String joinHate(UserDTO userDTO, Model model) {
		page = "hate";
		return "/join/select_hate";
	}
	
	//join_end
	@PostMapping("join_end")
	public String joinEnd(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
		//db에 맞게 고치기
		if(userDTO.getUserGender().equals("women")) { //성별
			userDTO.setUserGender("여자");
		} else if(userDTO.getUserGender().equals("man")) {
			userDTO.setUserGender("남자");
		}
		
		System.out.println("------end------");
		System.out.println(userDTO.getUserEmail());
		System.out.println(userDTO.getUserPwd());
		System.out.println(userDTO.getUserBirth());
		System.out.println(userDTO.getUserGender());
		
		//dto에 필수 값이 빠진 경우 메인으로 다시 보내기
		//error
		if(bindingResult.hasErrors()) {
			return "redirect:/main";
		}
		
		//dto값 db로 넘기기
		userService.create(userDTO.getUserEmail(), userDTO.getUserPwd(), 
				userDTO.getUserBirth(), userDTO.getUserGender(), 
				userDTO.getUserLike(), userDTO.getUserHate());
		
		page = "end";
		return "/join/complete";
	}
	
	//login
	//로그인 페이지
	@GetMapping("/login")
	public String loginPage(UserDTO userDTO, Model model) {
		page = "login";
		return "/login";
	}
	//처리
	@PostMapping("/login")
	public String login(@Valid UserDTO userDTO, BindingResult bindingResult, Model model, HttpSession session) {
		//아이디 or 이메일 입력 필드는 userdto의 userId 활용 -> userEmail을 사용하면 제약 걸림(@)
		//입력 칸을 채우지 않았을 경우
		if(userDTO.getUserId() == null) { //아이디 or 이메일이 null
			bindingResult.rejectValue("userId", "idIsNull", "이메일이나 아이디값을 입력해주세요.");
		}
		if(userDTO.getUserPwd() == null) { //비번 null
			bindingResult.rejectValue("userPwd", "PwdIsNull", "비밀번호를 입력해주세요.");
		}
		
		//디비에 값이 없을 경우
		boolean ck;
		ck = userService.existUser(userDTO.getUserId(), userDTO.getUserPwd());
		if(ck == false) {
			System.out.println("로그인 실패");
			bindingResult.rejectValue("userPwd", "UserIsNotExist", "아이디 혹은 비밀번호가 잘못되었습니다."); //정확히 pwd에 일어난 에러는 아니지만 위치상 여기에 처리
		}
		
		//에러가 있으면 로그인 화면으로 넘기기
		if(bindingResult.hasErrors()) { return "/login"; }
		else { //에러가 없음 -> 디비에도 값이 있음
			//세션 생성
			session.setAttribute("user", userDTO.getUserId());
			//메인으로 리다이렉트
			System.out.println("로그인 성공");
			return "redirect:/main";
		}
		
	}
	
	
}
