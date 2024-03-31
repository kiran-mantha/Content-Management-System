package com.example.cms.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.dto.UserRequest;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.User;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository repository;
	private ResponseStructure structure;

	public UserServiceImpl(UserRepository repository, ResponseStructure structure) {
		super();
		this.repository = repository;
		this.structure = structure;
	}
	private User mapToUser(UserRequest userRequest, User user) {
		user.setUserName(userRequest.getUserName());
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		return user;
	}

	@Override
	public ResponseEntity<ResponseStructure> userRegister(UserRequest userRequest) {
		
		structure.setStatuscode(HttpStatus.CREATED.value())
				 .setMessage("User Registered Successfully")
				 .setData(repository.save(mapToUser(userRequest, new User())));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(structure);
	}

	@Override
	public ResponseEntity<ResponseStructure> findUniqueUser(int userId) {
		Optional<User> optional = repository.findById(userId);
		
		if(optional.isPresent()) {
			User user = optional.get();
			structure.setStatuscode(HttpStatus.FOUND.value())
					 .setMessage("User Found based on Id")
					 .setData(optional.get());
			return ResponseEntity.status(HttpStatus.FOUND).body(structure);
		}
		else
			throw new UserNotFoundByIdException("User Not Found by provided Id");
	}

}
