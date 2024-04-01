package com.example.cms.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.exception.UserAlreadyExistsByEmailException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.User;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.UserRequest;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	
	private UserRepository repository;
	private ResponseStructure<UserResponse> structure;


	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest) {
		
		if(repository.existsByEmail(userRequest.getEmail())) throw new UserAlreadyExistsByEmailException("Failed to register user");
		
		return ResponseEntity.ok(structure.setStatuscode(HttpStatus.CREATED.value())
				 						  .setMessage("User Registered Successfully")
				 						  .setData(mapToUserResponse(repository.save(mapToUser(userRequest)))));
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUniqueUser(int userId) {
		
		return repository.findById(userId).map(user->ResponseEntity.ok(structure.setStatuscode(HttpStatus.FOUND.value())
																				.setMessage("User found by the given Id")
																				.setData(mapToUserResponse(user))))
										  .orElseThrow(() -> new UserNotFoundByIdException("User Not found by the Id: "+userId));
	}
	
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> softDeleteUser(int userId) {
		
		return repository.findById(userId).map(user->{
			user.setDeleted(true);
			return ResponseEntity.ok(structure.setStatuscode(HttpStatus.OK.value())
																   .setMessage("deleted set to true")
																   .setData(mapToUserResponse(repository.save(user))));
		}).orElseThrow(() -> new UserNotFoundByIdException("User not found by Id: "+userId));
	}

	private User mapToUser(UserRequest userRequest) {

		return User.builder().userName(userRequest.getUserName())
							 .email(userRequest.getEmail())
							 .password(userRequest.getPassword())
				   .build();
	}
	
	private UserResponse mapToUserResponse(User user) {
		
		return UserResponse.builder().userId(user.getUserId())
									 .username(user.getUserName())
									 .email(user.getEmail())
						   .build();
	}
}
