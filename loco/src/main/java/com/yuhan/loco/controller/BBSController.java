package com.yuhan.loco.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yuhan.loco.bbs.BBSDB;
import com.yuhan.loco.bbs.BBSDTO;
import com.yuhan.loco.bbs.BBSService;
import com.yuhan.loco.post.PostDB;
import com.yuhan.loco.post.PostDTO;
import com.yuhan.loco.post.PostService;
import com.yuhan.loco.user.UserDB;
import com.yuhan.loco.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//게시판 컨트롤러

@Controller
public class BBSController {
	String page = "";
	private final PostService postService;
	private final UserService userService;
	private final BBSService bbsService;
	public BBSController(PostService postService, UserService userService, BBSService bbsService) {
		this.postService = postService;
		this.userService = userService;
		this.bbsService = bbsService;
	}

	@GetMapping("/post")
	public String post(Model model, PostDB postDB)
	{
		List<BBSDB> bbs = this.bbsService.search();
		model.addAttribute("bbsDTO", bbs);
		return "/post/list";
	}
	@GetMapping("/bbs_write")
	public String write(Model model, HttpServletRequest req, BBSDB bbsDB, PostDB postDB) {
		String userId;
		HttpSession session = req.getSession(false);

		if(session != null) {
			userId = (String)session.getAttribute("user");
			UserDB userdb = userService.findUser(userId);
			model.addAttribute("userDTO", userdb);
			model.addAttribute("postDTO", new PostDB());
			System.out.println(userdb.getID());
			return "board/bbs_write";
		}
		model.addAttribute("bbsDB", bbsDB);
		model.addAttribute("postDB", postDB);
		return "/post/list";
	}

	@GetMapping("/bbs")
	public String bbs(Model model, BBSDB bbsDB)
	{
		page = "bbs";
		model.addAttribute("bbsDB", bbsDB);
		return "/board/bbs";
	}
	@PostMapping("post_write")
	public String write(PostDTO postDTO, BBSDTO bbsDTO, UserDB userDB, Model model, HttpServletRequest req) {
		LocalDateTime time = LocalDateTime.now();
		String timestr = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String userId, title;
		HttpSession session = req.getSession(false);
		userId = (String)session.getAttribute("user");
		title = postDTO.getTitle();
		if(page == "bbs") { //자유 게시판
			postDTO.setCategory("bbs");
			postDTO.setWriter(userId);
			postDTO.setComment("임시 댓글");//임시, 댓글 구현 시 삭제
			bbsDTO.setWriter(userId);
			bbsDTO.setTitle(title);
			bbsDTO.setCategory("bbs");
			bbsDTO.setDate(timestr);
			bbsDTO.setViews(0L);
			bbsDTO.setComment(0L);
		}
		postService.create(postDTO.getId(), postDTO.getCategory(), postDTO.getTitle(), postDTO.getWriter(), postDTO.getContent(), postDTO.getComment());
		bbsService.create(bbsDTO.getId(), bbsDTO.getTitle(), bbsDTO.getWriter(), bbsDTO.getCategory(), bbsDTO.getDate(), bbsDTO.getViews(), bbsDTO.getComment());
		model.addAttribute("bbsDTO", bbsDTO);
		return "/post/list";
	}
	
	
	@GetMapping("post_cancel")
	public String cancel() {
		return "/post/list";
	}
}