package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.dto.UserRequest;
import com.example.cms.utility.ResponseStructure;

public interface UserService {

	ResponseEntity<ResponseStructure> userRegister(UserRequest userRequest);

	ResponseEntity<ResponseStructure> findUniqueUser(int userId);

}
