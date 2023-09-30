package com.yuhan.loco.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuhan.loco.user.EmailSend;

@RestController
@RequestMapping(value="/api", method=RequestMethod.POST)
public class apiController {
	@Autowired
	private EmailSend emailsend;

	@GetMapping(value="/email_auth", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> emailAuth(@RequestBody HashMap<String, Object> user){
		String username = (String) user.get("useremail");
		String authNum = emailsend.joinEmail(username);
		return ResponseEntity.status(HttpStatus.OK).body(authNum);
	}
}
