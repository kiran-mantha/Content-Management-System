package com.example.cms.responsedto;

import com.example.cms.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogResponse {

	private int blogId;
	private String title;
	private String[] topics;
	private String summary;
	private User user;
}
