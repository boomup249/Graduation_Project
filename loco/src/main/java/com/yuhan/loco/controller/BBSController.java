package com.yuhan.loco.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String post(Model model,
			@RequestParam(value = "pages", defaultValue = "1") int pages,
			@RequestParam(value = "orderby", defaultValue = "id") String order, PostDB postDB)
	{
		pages -= 1;
		Page<BBSDB> bbs = this.bbsService.search(pages);
		if(page == "bbs") {bbs = this.bbsService.findbbs(pages);}
		else if(page == "notice") {bbs = this.bbsService.findnotice(pages);}
		else if(page == "party") {bbs = this.bbsService.findparty(pages);}
		else if(page == "guide") {bbs = this.bbsService.findguide(pages);}
				int currentPage = pages + 1;
				int calcEnd = (int)(Math.ceil(currentPage / 10.0) * 10);
				int startPage = calcEnd - 9;
				int endPage = Math.min(calcEnd, bbs.getTotalPages());
		model.addAttribute("bbsDTO", bbs);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", bbs.getTotalPages());
		model.addAttribute("bbsService", bbsService);
		return "/post/list";
	}
	@GetMapping("/list/search")
	public String searchres(Model model,
			@RequestParam(value = "pages", defaultValue = "1") int pages,
			@RequestParam(value = "orderby", defaultValue = "id") String order, String search, PostDB postDB) {
		pages -= 1;
		Page<BBSDB> bbs = this.bbsService.findbytitle(search, pages);
		int currentPage = pages + 1;
		int calcEnd = (int)(Math.ceil(currentPage / 10.0) * 10);
		int startPage = calcEnd - 9;
		int endPage = Math.min(calcEnd, bbs.getTotalPages());
		model.addAttribute("bbsDTO", bbs);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", bbs.getTotalPages());
		model.addAttribute("bbsService", bbsService);
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
	@GetMapping("/notice")
	public String notice(Model model, BBSDB bbsDB)
	{
		page = "notice";
		model.addAttribute("bbsDB", bbsDB);
		return "/board/bbs";
	}
	@GetMapping("/guide")
	public String guide(Model model, BBSDB bbsDB)
	{
		page = "guide";
		model.addAttribute("bbsDB", bbsDB);
		return "/board/bbs";
	}
	@GetMapping("/party")
	public String party(Model model, BBSDB bbsDB)
	{
		page = "party";
		model.addAttribute("bbsDB", bbsDB);
		return "/board/bbs";
	}
	@PostMapping("post_write")
	public String write(PostDTO postDTO, BBSDTO bbsDTO, UserDB userDB, Model model, HttpServletRequest req) {
		LocalDateTime time = LocalDateTime.now();
		String timestr = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String userId, title, category;
		HttpSession session = req.getSession(false);
		userId = (String)session.getAttribute("user");
		title = postDTO.getTitle();
		category = postDTO.getCategory();
		if(category == "bbs") { //자유 게시판
			postDTO.setCategory("bbs");
			bbsDTO.setCategory("bbs");			
		}
		else if(category == "guide") {
			postDTO.setCategory("guide");
			bbsDTO.setCategory("guide");
		}
		else if(category == "party") {
			postDTO.setCategory("party");
			bbsDTO.setCategory("party");
		}
		postDTO.setWriter(userId);
		postDTO.setComment("임시 댓글");//임시, 댓글 구현 시 삭제
		bbsDTO.setWriter(userId);
		bbsDTO.setTitle(title);		
		bbsDTO.setDate(timestr);
		bbsDTO.setViews(0L);
		bbsDTO.setComment(0L);
		postService.create(postDTO.getId(), postDTO.getCategory(), postDTO.getTitle(), postDTO.getWriter(), postDTO.getContent(), postDTO.getComment());
		bbsService.create(bbsDTO.getId(), bbsDTO.getTitle(), bbsDTO.getWriter(), bbsDTO.getCategory(), bbsDTO.getDate(), bbsDTO.getViews(), bbsDTO.getComment());
		model.addAttribute("bbsDTO", bbsDTO);
		return "redirect:/post";
	}
	@GetMapping("post_cancel")
	public String cancel() {
		return "/post/list";
	}
	
	@GetMapping("/article/{id}")
	public String show_article(@PathVariable Long id, Model model, BBSDB bbsDB) {
		PostDB article = postService.getByID(id);
		bbsService.viewerup(id);
		model.addAttribute("postDTO", article);
		return "/board/article";
	}
}