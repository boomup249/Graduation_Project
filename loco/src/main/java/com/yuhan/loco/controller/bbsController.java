package com.yuhan.loco.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.yuhan.loco.myapp.Post;
import com.yuhan.loco.user.UserDB;
import com.yuhan.loco.user.UserDTO;
import com.yuhan.loco.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller



public class bbsController {
	/*
	UserService userservice;
	@PostMapping("/post")
	public String createPost(@RequestBody Post post) {
		return "/post/list";
       
    }*/

   @GetMapping("/bbs")
   public String bbs() {
      return "/game/bbs";
      }
   @GetMapping("/post")
    public String listPosts(Model model) {
        return "/post/list";
    }
   
   @GetMapping("/bbs_notice")
   public String bbsnotice() {
      return "/game/bbs_notice";
   }
   
   @GetMapping("/bbs_write")
   public String bbswrite(Model model, UserDTO userDTO) {
      
      return "/game/bbs_write";
   }

   /*
   @GetMapping("/bbs_write")
   public String bbswrite(Model model, UserDTO userDTO, HttpServletRequest req) {
	   String userId;

       if(req.getSession(false) != null) { //로그인?
           HttpSession session = req.getSession(false);
           userId = (String)session.getAttribute("user");

           UserDB userdb = userservice.findUser(userId);

           model.addAttribute("userDTO", userdb);

           return "main";
       }
       return "main"; //여기 주소만 고쳐
       }*/
   
}