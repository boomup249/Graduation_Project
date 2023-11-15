package com.yuhan.loco.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuhan.loco.bbs.BBSDB;
import com.yuhan.loco.bbs.BBSDTO;
import com.yuhan.loco.bbs.BBSService;
import com.yuhan.loco.bbs.CommentDB;
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
		Pageable pageable = PageRequest.of(pages, 15);
		Page<BBSDB> bbs = this.bbsService.search(pages, pageable);
		if("bbs".equals(page)) {bbs = this.bbsService.findbbs(pages, pageable);}
		else if("notice".equals(page)) {bbs = this.bbsService.findnotice(pages, pageable);}
		else if("party".equals(page)) {bbs = this.bbsService.findparty(pages, pageable);}
		else if("guide".equals(page)) {bbs = this.bbsService.findguide(pages, pageable);}
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
	public String searchres(Model model, String search, PostDB postDB) {
		List<BBSDB> bbs = bbsService.findbytitlelist(search);	
		model.addAttribute("bbsDTO", bbs);
		model.addAttribute("bbsService", bbsService);
		return "/post/searchlist";
	}
	@GetMapping("/new")
	public String sortbyNew(Model model,
			@RequestParam(value = "pages", defaultValue = "1") int pages,
			@RequestParam(value = "orderby", defaultValue = "date") String order, PostDB postDB) {
		pages -= 1;
		Pageable pageable = PageRequest.of(pages, 15, Sort.by("date").descending());
		Page<BBSDB> bbs = this.bbsService.search(pages, pageable);
		if("bbs".equals(page)) {bbs = this.bbsService.findbbs(pages, pageable);}
		else if("notice".equals(page)) {bbs = this.bbsService.findnotice(pages, pageable);}
		else if("party".equals(page)) {bbs = this.bbsService.findparty(pages, pageable);}
		else if("guide".equals(page)) {bbs = this.bbsService.findguide(pages, pageable);}
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
	@GetMapping("/views")
	public String sortbyView(Model model,
			@RequestParam(value = "pages", defaultValue = "1") int pages,
			@RequestParam(value = "orderby", defaultValue = "views") String order, PostDB postDB) {
		pages -= 1;
		Pageable pageable = PageRequest.of(pages, 15, Sort.by("views").descending());
		Page<BBSDB> bbs = this.bbsService.search(pages, pageable);
		if("bbs".equals(page)) {bbs = this.bbsService.findbbs(pages, pageable);}
		else if("notice".equals(page)) {bbs = this.bbsService.findnotice(pages, pageable);}
		else if("party".equals(page)) {bbs = this.bbsService.findparty(pages, pageable);}
		else if("guide".equals(page)) {bbs = this.bbsService.findguide(pages, pageable);}
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
		String getuserId, userId;
		HttpSession session = req.getSession(false);
		if(session != null) {
			getuserId = (String)session.getAttribute("user");
			userId = userService.findUserId(getuserId);
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
		String getuserId, userId, title, category;
		HttpSession session = req.getSession(false);
		getuserId = (String)session.getAttribute("user");
		userId = userService.findUserId(getuserId);
		title = postDTO.getTitle();
		if (title == null || title.trim().isEmpty()) {
	        return "/uperror";
	    }
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
		bbsDTO.setWriter(userId);
		bbsDTO.setTitle(title);
		bbsDTO.setDate(timestr);
		bbsDTO.setViews(0L);
		bbsDTO.setComment(0L);
		bbsService.create(bbsDTO.getTitle(), bbsDTO.getWriter(), bbsDTO.getCategory(), bbsDTO.getDate(), bbsDTO.getViews(), bbsDTO.getComment());
		List<BBSDB> bbsDBList = bbsService.findIDBytitledate(bbsDTO.getTitle(), bbsDTO.getDate());
		BBSDB bbsDB = bbsDBList.get(0);
		Long bbs_Id = bbsDB.getId();
		postService.create(bbs_Id, postDTO.getCategory(), postDTO.getTitle(), postDTO.getWriter(), postDTO.getContent());
		model.addAttribute("bbsDTO", bbsDTO);
		return "redirect:/post";
	}
	@GetMapping("post_cancel")
	public String cancel() {
		return "redirect:/post";
	}

	@GetMapping("/article/{id}")
	public String show_article(@PathVariable Long id, HttpServletRequest req, Model model, BBSDB bbsDB){
		HttpSession session = req.getSession(false);
		if(session == null) {
			return "/loginerror";
		}
		PostDB article = postService.getByID(id);
		String getuserId = (String)session.getAttribute("user");
		String userId = userService.findUserId(getuserId);
		UserDB userdb = userService.findUser(userId);
		model.addAttribute("userDTO", userdb);

		List<CommentDB> comments = bbsService.findCommentDB(id);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@"+comments);
		if(comments != null && !comments.isEmpty()) {
			model.addAttribute("comments", comments);
		}
		bbsService.viewerup(id);
		model.addAttribute("postDTO", article);
		return "/board/article";
	}
}