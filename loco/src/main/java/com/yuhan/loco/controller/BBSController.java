package com.yuhan.loco.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuhan.loco.prefer.PreferService;

import java.util.ArrayList;
import java.util.List;

//게시판 컨트롤러

@Controller
public class BBSController {
	/*
	//전역
	String page = "";
	
	//서비스 나중에 만들고 바꾸기
	private final GameService gameService;
	private final PreferService preferService;

	public GameController(GameService gameService, PreferService preferService) {
        this.gameService = gameService;
        this.preferService = preferService;
    }
	
	
	//게임 목록 페이지에 맞게 가공
	private Page<GameDTO> createPageableList(List<GameDTO> fullList, int page, int pageSize) {
	    int start = page * pageSize; //0부터 시작하므로, 곱하면 시작 배열 index 나옴
	    int end = Math.min(start + pageSize, fullList.size()); //남은 페이지 항목이 10개 미만일 수 있으므로

	    List<GameDTO> sublist = fullList.subList(start, end); //해당 페이지에 들어갈 리스트

	    return new PageImpl<>(sublist, PageRequest.of(page, pageSize), fullList.size());
	}
	
	
	//연결
	//pc
	@GetMapping("/pc")
	public String pc(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
		//(1안 기준)디비 확정되면 디비에 맞춰 바꾸기 -> 디비에서 받아와서 가공해서 dto에 차례로 넣고 dtoList 넘기기
		
		//페이징용 page, pageable은 0부터 시작함 -> -1로 가공해주기, html에서도 가공 필요
		page -= 1;
		
		//dto리스트 전체 불러오기
		List<GameDTO> fullList = createFullGameList();
		
		// 전체 데이터 리스트에서 해당 페이지의 데이터만 추출하여 페이징
		int pageSize = 10; //한 페이지에 나올 행 수
		Page<GameDTO> gamePage = createPageableList(fullList, page, pageSize); //페이징 끝
		
		//페이지네이션 정보 가공: 시작 페이지 번호, 현재 페이지 번호, 끝 페이지 번호
		int currentPage = page + 1; //현재 페이지 번호
		int calcEnd = (int)(Math.ceil(currentPage / 10.0) * 10); //현재 페이지를 10으로 나눈 후 올리고 10을 곱하면 끝번호가 나옴(ex. 3-> 0.3 - 1 - 10, 23-> 2.3 - 3 - 30)
		int startPage = calcEnd - 9; //시작 페이지 번호
		
		//끝 페이지 번호 확정
		int endPage = Math.min(calcEnd, gamePage.getTotalPages());
		
		//페이징 객체 model에 넣기
		model.addAttribute("gamePage", gamePage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPage", gamePage.getTotalPages());
		
		return "/game/pc";
	}
	
	//console
	@GetMapping("/console")
	public String console(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
			//(1안 기준)디비 확정되면 디비에 맞춰 바꾸기 -> 디비에서 받아와서 가공해서 dto에 차례로 넣고 dtoList 넘기기
			
			//페이징용 page, pageable은 0부터 시작함 -> -1로 가공해주기, html에서도 가공 필요
			page -= 1;
			
			//dto리스트 전체 불러오기
			List<GameDTO> fullList = createFullGameList();
			
			// 전체 데이터 리스트에서 해당 페이지의 데이터만 추출하여 페이징
			int pageSize = 10; //한 페이지에 나올 행 수
			Page<GameDTO> gamePage = createPageableList(fullList, page, pageSize); //페이징 끝
			
			//페이지네이션 정보 가공: 시작 페이지 번호, 현재 페이지 번호, 끝 페이지 번호
			int currentPage = page + 1; //현재 페이지 번호
			int calcEnd = (int)(Math.ceil(currentPage / 10.0) * 10); //현재 페이지를 10으로 나눈 후 올리고 10을 곱하면 끝번호가 나옴(ex. 3-> 0.3 - 1 - 10, 23-> 2.3 - 3 - 30)
			int startPage = calcEnd - 9; //시작 페이지 번호
			
			//끝 페이지 번호 확정
			int endPage = Math.min(calcEnd, gamePage.getTotalPages());
			
			//페이징 객체 model에 넣기
			model.addAttribute("gamePage", gamePage);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("totalPage", gamePage.getTotalPages());
			
			return "/game/console";
		}
	*/
	@GetMapping("/bbs")
	public String bbs() {
			
			return "/game/bbs";
		}
	
	
}
