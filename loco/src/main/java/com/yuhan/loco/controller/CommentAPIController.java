package com.yuhan.loco.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuhan.loco.bbs.BBSService;
import com.yuhan.loco.bbs.CommentReqDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/comm")
@RestController
public class CommentAPIController {
 
	private final BBSService bbsService;
	public CommentAPIController(BBSService bbsService) {
		this.bbsService = bbsService;
	}
 	@PostMapping("/article/{id}/comments")
	public ResponseEntity commentSave(@PathVariable Long id, @RequestBody CommentReqDTO comm, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		String userId = (String)session.getAttribute("user");
		return ResponseEntity.ok(bbsService.commentSave(userId, id, comm));
 	}
}
