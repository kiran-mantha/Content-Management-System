package com.example.cms.requestdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogPostRequest {

	@NotNull
	private String title;
	private String subTitle;
	@Size(min = 500, message = "Field must be at least 500 characters long!")
	private String summary;
}
