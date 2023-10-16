package com.yuhan.loco.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuhan.loco.news.NewsDTO;
import com.yuhan.loco.news.NewsService;

@Controller
public class NewsController {
	//전역
	String page = "";
		
	//
	private final NewsService nService;
	
	public NewsController(NewsService nService) {
		this.nService = nService;
	}
    
	//News
    @GetMapping("/news")
    public String news() {
    	
        return "/calendar/news"; 
    }
    
    @GetMapping("/news_event") //뉴스 이벤트 받아오기
	@ResponseBody
	public List<NewsDTO> getEvent() {
		return nService.getCalendarNews();
	}
    
    @GetMapping("/news_memo")
    @ResponseBody
    public List<NewsDTO> getMemoNews() {
        return nService.getMemoNews();
    }

}
