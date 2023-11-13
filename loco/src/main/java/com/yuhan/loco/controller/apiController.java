package com.yuhan.loco.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuhan.loco.bbs.BBSService;
import com.yuhan.loco.prefer.PreferDB;
import com.yuhan.loco.prefer.PreferDTO;
import com.yuhan.loco.prefer.PreferService;
import com.yuhan.loco.profile.ProfileRepository;
import com.yuhan.loco.profile.ProfileService;
import com.yuhan.loco.profile.profileDB;
import com.yuhan.loco.search.GameSearchDB;
import com.yuhan.loco.search.GameSearchService;
import com.yuhan.loco.user.EmailSend;
import com.yuhan.loco.user.UserDB;
import com.yuhan.loco.user.UserDTO;
import com.yuhan.loco.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping(value="/api",method = RequestMethod.POST)
public class apiController {
	@Autowired
	private EmailSend emailsend;
	private final PreferService preferService;
	private final UserService userService;
	private final ProfileService profileService;
	private final PasswordEncoder passwordEncoder;
	private final GameSearchService gamesearchService;
	private final BBSService bbsService;
	private UserDB userdb = new UserDB();
	private profileDB profiledb = new profileDB();
	
	public apiController(PreferService preferService, UserService userService, ProfileService profileService 
						 ,PasswordEncoder passwordEncoder, GameSearchService gamesearchService, BBSService bbsService) {
        this.preferService = preferService;
        this.userService = userService;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
        this.gamesearchService = gamesearchService;
        this.bbsService = bbsService;
    }
	
	@GetMapping(value="/email_auth", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> emailAuth(@RequestBody HashMap<String, Object> user){
		String username = (String) user.get("useremail");
		String authNum = emailsend.joinEmail(username);
		System.out.println(username + " *** " +authNum);
		return ResponseEntity.status(HttpStatus.OK).body(authNum);
	}
	
	@PostMapping("/genreLike")
	public String handleRequest(@RequestBody Map<String, Object> genresMap, Model model,HttpServletRequest req) {
		List<String> genres = (List<String>) genresMap.get("genre");
	    String getuserId, userId;
	    StringBuilder genreList = new StringBuilder();
	    if(req.getSession(false) != null) { //로그인?
	        HttpSession session = req.getSession(false);
	        getuserId = (String)session.getAttribute("user");
	        userId = userService.findUserId(getuserId);

	        UserDB userdb = userService.findUser(userId);
	        PreferDB preferdb = preferService.findUser(userId);

	        PreferDTO preferDTO = new PreferDTO();
	        preferDTO.setUserLike(preferdb.getLove());

	        List<PreferDB> userSelect = preferService.getprefer();

	        model.addAttribute("userDTO", userdb);
	        model.addAttribute("preferDTO", preferDTO); 
	        model.addAttribute("userSelect", userSelect);
	        model.addAttribute("preferdb", preferdb); 
	        
			if(genres != null && !genres.isEmpty()) {
				for(String genre : genres) {
					genreList.append(genre);
					genreList.append(",");
					
				}
				if(genreList.length() > 0) {
					genreList.deleteCharAt(genreList.length() - 1);
					//System.out.println(genreList);
					preferDTO.setUserLike(genreList.toString());
					preferService.updateGenre(preferDTO,preferdb);
				}
			}
			else {
				System.out.println("선택된 장르없음");
			}
	    }
		return "myaccount/profile";
	}
	
	@PostMapping("/genreHate")
	public String handleRequestHate(@RequestBody Map<String, Object> genresMap, Model model,HttpServletRequest req) {
		List<String> genres = (List<String>) genresMap.get("genre");
	    String userId;
	    StringBuilder genreList = new StringBuilder();
	    if(req.getSession(false) != null) { //로그인?
	        HttpSession session = req.getSession(false);
	        userId = (String)session.getAttribute("user");
	        

	        UserDB userdb = userService.findUser(userId);
	        PreferDB preferdb = preferService.findUser(userId);

	        PreferDTO preferDTO = new PreferDTO();
	        preferDTO.setUserHate(preferdb.getDislike());
	        preferDTO.setUserLike(preferdb.getLove());
	        List<PreferDB> userSelect = preferService.getprefer();

	        model.addAttribute("userDTO", userdb);
	        model.addAttribute("preferDTO", preferDTO); 
	        model.addAttribute("userSelect", userSelect);
	        model.addAttribute("preferdb", preferdb);
	        
	        
			if(genres != null && !genres.isEmpty()) {
				for(String genre : genres) {
					genreList.append(genre);
					genreList.append(",");
					
				}
				if(genreList.length() > 0) {
					genreList.deleteCharAt(genreList.length() - 1);
					//System.out.println(genreList);
					preferDTO.setUserHate(genreList.toString());
					preferService.updateGenreHate(preferDTO,preferdb);
				}
			}
			else {
				System.out.println("선택된 장르없음");
			}
	    }
		return "myaccount/profile";
	}
	
	@PostMapping("/userInfo")
	public String userInfo(@RequestBody Map<String, String> userId_fdata,
			Model model,HttpServletRequest req) {
		String getuserId, userId;
		String userId_f = userId_fdata.get("id");
	    if(req.getSession(false) != null) { //로그인?
	        HttpSession session = req.getSession(false);
	        getuserId = (String)session.getAttribute("user");
	        userId = userService.findUserId(getuserId);

	        UserDB userdb = userService.findUser(userId);
	        PreferDB preferdb = preferService.findUser(userId);
	        
	        PreferDTO preferDTO = new PreferDTO();
	        preferDTO.setUserHate(preferdb.getDislike());

	        List<PreferDB> userSelect = preferService.getprefer();


	        model.addAttribute("userDTO", userdb);
	        model.addAttribute("preferDTO", preferDTO); 
	        model.addAttribute("userSelect", userSelect);
	        model.addAttribute("preferdb", preferdb);
	    }
		return "myaccount/profile";
	}
	
	//기존비밀번호 체크
	@PostMapping(value = "/checkPwd", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> check_pwd(@RequestBody Map<String, String> user,HttpServletRequest req){
		String getuserId, userId;
		boolean check = false;
		 if(req.getSession(false) != null) {
	        HttpSession session = req.getSession(false);
	        getuserId = (String)session.getAttribute("user");
	        userId = userService.findUserId(getuserId);
	        UserDB userdb = userService.findUser(userId);
	        
			String userpwd = (String) user.get("pwd");
			
			String dbpwd = userdb.getPWD();
			check = passwordEncoder.matches(userpwd, dbpwd);
			
		 }
		 return ResponseEntity.status(HttpStatus.OK).body(check);
		}
	//비밀번호 db저장
	@PostMapping(value = "/UpdatekPwd", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public String Updatepwd(@RequestBody Map<String, String> user,HttpServletRequest req){
		String getuserId, userId;
		 if(req.getSession(false) != null) {
	        HttpSession session = req.getSession(false);
	        getuserId = (String)session.getAttribute("user");
	        userId = userService.findUserId(getuserId);
	        UserDB userdb = userService.findUser(userId);
			String userpwd = (String) user.get("new_pw");
			String encodepwd = userService.encodePWD(userpwd);
			UserDTO userDTO = new UserDTO();
			userDTO.setUserPwd(encodepwd);
			userService.updatePWD(userDTO, userdb);
			
		 }
		 return "myaccount/profile";
		}
	
	//마이페이지
	@PostMapping(value = "/Uploadprofile", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> uploadProfile(@RequestBody Map<String, String> data, HttpServletRequest req,Model model) {
	    String getuserId, userId;
        String nickname = data.get("nickname");
        String des = data.get("des");
	    if (req.getSession(false) != null) {
	        HttpSession session = req.getSession(false);
	        getuserId = (String) session.getAttribute("user");
	    	userId = userService.findUserId(getuserId);
	        
	        profiledb = profileService.findUser(userId);
	        
	        if (nickname.matches("\\s*")) {
	        	nickname = profiledb.getNICKNAME();
	        }
	        profileService.saveMypage(userId, nickname, des);
	    }
	    String response = "Success";
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/UploadImg", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> showUploadForm(@RequestParam("uploadedFiles") MultipartFile uploadedFile, HttpServletRequest req,Model model) {
	    String getuserId, userId;
	    if (req.getSession(false) != null) {
	        HttpSession session = req.getSession(false);
	        getuserId = (String) session.getAttribute("user");
	        userId = userService.findUserId(getuserId);
	        
	        try {
	        	byte[] imageBytes = uploadedFile.getBytes(); // MultipartFile을 byte[]로 변환
	        	profileService.saveImage(userId, imageBytes);
	        	return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
	        }catch(IOException e) {
	        	return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }else {
	            return new ResponseEntity<>("Session not found", HttpStatus.UNAUTHORIZED);
	        }
	        
	}
	@PostMapping(
			value = "/search/{word}",
			produces = "application/json; charset=utf8"
			)
	public ResponseEntity<List<GameSearchDB>> getContainWord(@PathVariable String word) {			
			String regex = ".*" + word + ".*";
			List<GameSearchDB> list = gamesearchService.SearchList(regex);
			for(GameSearchDB item : list)
	            System.out.println("***" + item);
			return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
 	@PostMapping(value="/article/{id}/comments", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity commentSave(@PathVariable Long id, @RequestBody Map<String, String> data, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		String getuserId = (String)session.getAttribute("user");
		String userId = userService.findUserId(getuserId);
		
		String comment = (String)data.get("comment");
		
		bbsService.commentcreate(id, comment, userId);
		bbsService.commentup(id);
		
		return ResponseEntity.ok().build();
 	}
}
	

