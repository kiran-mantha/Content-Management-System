package com.example.cms.requestdto;

import com.example.cms.model.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogRequest {

	@NotNull
	@Pattern(regexp = "[a-zA-Z", message = "Enter title")
	private String title;
	private String[] topics;
	private String summary;
	private User user;
}
