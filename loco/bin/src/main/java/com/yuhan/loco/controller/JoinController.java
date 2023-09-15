package com.yuhan.loco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuhan.loco.prefer.PreferDTO;
import com.yuhan.loco.prefer.PreferService;
import com.yuhan.loco.user.EmailSend;
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
 * <완료>6. 로그인 관련
 * */

@Controller
public class JoinController {
	//전역
	String page = "";
	private final UserService userService;
	private final PreferService preferService;
	EmailSend emailsend = new EmailSend();
	
	public JoinController(UserService userService, PreferService preferService) {
        this.userService = userService;
        this.preferService = preferService;
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
	@PostMapping("email_ck") //이메일이 디비에 있는지 확인
	public String emailCheck(UserDTO userDTO, Model model) {
		page = "email_ck";
		
		boolean ck;
		ck = userService.existIdOrEmail(userDTO.getUserEmail()); //이메일이 디비에 있는지 확인
		
		//get매핑으로 param 넘겨버리기
		if(ck) { //이메일이 디비에 있다면
			return "redirect:/login?ad=" + userDTO.getUserEmail(); //로그인 화면으로 넘기기
		} else { //이메일이 디비에 없다면 -> 새로운 가입
			return "redirect:/email_verify?ad=" + userDTO.getUserEmail();
		}
	}
	
	//email_verify
	@GetMapping("email_verify")
	public String emailVerify(UserDTO userDTO, @RequestParam(value="ad"/*, defaultValue = "" <단계를 안 거친 경우 못 들어오게>*/) String email, Model model) {
		userDTO.setUserEmail(email);
		//email.joinEmail(userDTO.getUserEmail());
		System.out.println("이메일 인증 요청");
		return "/join/email_vf";
	}
	//★post 매핑으로 인증번호 확인 구현 -> 이 매핑 주소를 email_vf.html의 action으로 넣기
	//+ 이 과정에서 다음 단계인 join_id로 email 주소를 넘겨줘야 함
	// -> id.html에 hidden으로 이메일값 넣어주기: 칸은 만들어져 있으니 value에 넣기만하면 됨

	//join_id
	@GetMapping("join_id")
	public String joinId(UserDTO userDTO, Model model) {
		page = "id";
		return "/join/id";
	}
	@PostMapping("/check_id") //아이디 중복체크(ajax)
	@ResponseBody
	public boolean idCheck(@RequestParam("id") String id) {
		boolean ck = userService.existIdOrEmail(id);
		return ck;
	}
	
	//※아래부터는 이전 값을 받아와야해서 post가 필요
	//join_pwd
	@PostMapping("join_pwd")
	public String joinPwd(@Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
		//아이디 중복 확인은 따로 처리
		
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
	public String joinLike(@Valid UserDTO userDTO, BindingResult bindingResult, PreferDTO preferDTO, Model model) {
							//(인수)prefer는 필수 선택 아니라 validation 지우고 뒤로 미뤘어용! @valid랑 bindingresult랑 떨어져 있으면 오류나서 순서 고쳤습니다.  <- 유지아
							//prefer도 필수 선택으로 바뀌면 조정 필요해요!
		if(page.equals("info")) {
			//gender 미선택 후 넘김
			if(userDTO.getUserGender() == null) {
				bindingResult.rejectValue("userGender", "genderIsNull", "성별은 필수 선택입니다.");
			}
			
			//birth 미선택 후 넘기면 어차피 haserror 뜸 -> 형식 안 맞아서
			if(bindingResult.hasFieldErrors("userBirh")) { //info에서 birth 안 선택하고 넘기면 에러 발생
				userDTO.setUserBirth(null);
			}
			
			//error
			if(bindingResult.hasErrors()) { return "/join/select_info"; }
			
		}
		
		page = "like";
		return "/join/select_like";
	}
	
	//join_hate
	@PostMapping("join_hate")
	public String joinHate(UserDTO userDTO, PreferDTO preferDTO, Model model) {
		page = "hate";
		return "/join/select_hate";
	}
	
	//join_end
	@PostMapping("join_end")
	public String joinEnd(@Valid UserDTO userDTO, @Valid PreferDTO preferDTO, BindingResult bindingResult, Model model) {
		//db에 맞게 고치기
		if(userDTO.getUserGender().equals("women")) { //성별
			userDTO.setUserGender("여자");
		} else if(userDTO.getUserGender().equals("man")) {
			userDTO.setUserGender("남자");
		}
		
		System.out.println("------end------");
		System.out.println(userDTO.getUserEmail());
		System.out.println(userDTO.getUserId());
		System.out.println(userDTO.getUserPwd());
		System.out.println(userDTO.getUserBirth());
		System.out.println(userDTO.getUserGender());
		System.out.println(preferDTO.getUserLike());
		System.out.println(preferDTO.getUserHate());
		
		//dto에 필수 값이 빠진 경우 메인으로 다시 보내기
		//error
		//if(bindingResult.hasErrors()) {
		//	return "redirect:/main";
		//}
		
		//dto값 db로 넘기기
		userService.create(userDTO.getUserEmail(), userDTO.getUserId(), userDTO.getUserPwd(), 
				userDTO.getUserBirth(), userDTO.getUserGender());
		preferService.create(userDTO.getUserId(), preferDTO.getUserLike(), preferDTO.getUserHate());
		
		page = "end";
		return "/join/complete";
	}
	
	
}
