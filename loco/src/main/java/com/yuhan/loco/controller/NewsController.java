package com.yuhan.loco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {
	//전역
		String page = "";
		
		//
    
	//News
    @GetMapping("/news")
    public String news() {
        return "/calendar/news"; 
    }

}
