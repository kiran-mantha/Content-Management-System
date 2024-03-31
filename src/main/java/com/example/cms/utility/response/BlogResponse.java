package com.example.cms.utility.response;

import com.example.cms.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogResponse {

	private int blogId;
	private String title;
	private String[] topics;
	private String summary;
	private User user;
}
