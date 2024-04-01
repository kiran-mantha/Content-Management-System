package com.example.cms.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {

	@NotNull(message = "Invalid Input")
	private String userName;
	@NotNull(message = "Invalid Input")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	private String email;
	@NotNull(message = "Invalid Input")
	private String password;
	private boolean deleted;
}
