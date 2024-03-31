package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.dto.UserRequest;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserService service;

	public UserController(UserService service) {
		super();
		this.service = service;
	}

	@PostMapping("/register")
	public ResponseEntity<ResponseStructure> userRegister(@RequestBody UserRequest userRequest) {
		return service.userRegister(userRequest);
	}
	
	@GetMapping("{userId}")
	public ResponseEntity<ResponseStructure> findUniqueUser(@PathVariable int userId) {
		return service.findUniqueUser(userId);
	}
}
 