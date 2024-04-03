package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.UserRequest;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.service.UserService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@Operation(description = "Insert User", responses = {
			@ApiResponse(responseCode = "200", description = "Inserted Successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid Inputs", content = {
					@Content(schema = @Schema(implementation = ErrorStructure.class))
			})
	})
	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody UserRequest userRequest) {
		return service.registerUser(userRequest);
	}
	
	@GetMapping("{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUniqueUser(@PathVariable int userId) {
		return service.findUniqueUser(userId);
	}
	
	@DeleteMapping("{userId}")
	private ResponseEntity<ResponseStructure<UserResponse>> softDeleteUser(@PathVariable int userId) {
		return service.softDeleteUser(userId);
	}
}
 