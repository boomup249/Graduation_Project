package com.yuhan.loco.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuhan.loco.prefer.PreferDB;
import com.yuhan.loco.prefer.PreferDTO;
import com.yuhan.loco.prefer.PreferService;
import com.yuhan.loco.profile.ProfileService;
import com.yuhan.loco.profile.profileDB;
import com.yuhan.loco.user.UserDB;
import com.yuhan.loco.user.UserDTO;
import com.yuhan.loco.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	private final ProfileService profileService;

	public MypageController(UserService userService, PreferService preferService, ProfileService profileService) {
        this.userService = userService;
        this.preferService = preferService;
        this.profileService = profileService;
    }


	//연결
	//join_modify < modify_info.html을 작동시키기 위한 코드(임의로 넣어놓은 거라 이후에 수정 부탁) >
	@GetMapping("join_modify")
	public String joinModify(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		page = "modify";
		return "/join/modify_info";
		}

	@RequestMapping("/profile")
	public String profile(Model model, HttpServletRequest req) {
	    String getuserId, userId;

	    if(req.getSession(false) != null) { //로그인?
	        HttpSession session = req.getSession(false);
	        getuserId = (String)session.getAttribute("user");
	        userId = userService.findUserId(getuserId);

	        UserDB userdb = userService.findUser(userId);
	        PreferDB preferdb = preferService.findUser(userId);
	        profileDB profiledb = profileService.findUser(userId);

	        PreferDTO preferDTO = new PreferDTO();
	        preferDTO.setUserLike(preferdb.getLove());
	        System.out.println("&&&&&&&&&&"+preferDTO.getUserLike());


	        List<PreferDB> userSelect = preferService.getprefer();
	        String profile_img = profileService.convertByteToBase64(profiledb.getIMG());
	        model.addAttribute("userDTO", userdb);
	        model.addAttribute("preferDTO", preferDTO); // 모델 속성 이름을 "preferDTO"로 변경
	        model.addAttribute("userSelect", userSelect);
	        model.addAttribute("preferdb", preferdb); // 모델 속성 이름을 "preferdb"로 변경
	        model.addAttribute("profile_img", profile_img);

	    }

	    page = "profile";

	    return "myaccount/profile";
	}


	@GetMapping("/mypage")
    public String Mypage(Model model, HttpServletRequest req) {
        String getuserId, userId;

        if(req.getSession(false) != null) { //로그인?
            HttpSession session = req.getSession(false);
            getuserId = (String)session.getAttribute("user");
            userId = userService.findUserId(getuserId);

            UserDB userdb = userService.findUser(userId);
            PreferDB preferdb = preferService.findUser(userId);
            profileDB profiledb = profileService.findUser(userId);
            String nickname = profiledb.getNICKNAME();
            String profile_img = profileService.convertByteToBase64(profiledb.getIMG());

            model.addAttribute("profiledb",profiledb);
            model.addAttribute("profile_img", profile_img);
            model.addAttribute("userDTO", userdb);
            model.addAttribute("preferDTO", preferdb);

        }
        page = "mypage";
		return "/myaccount/mypage";
	}

}
