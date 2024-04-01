package com.example.cms.responsedto;

import java.time.LocalDateTime;

import com.example.cms.enums.PostType;
import com.example.cms.model.Blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogPostResponse {

	private int postId;
	private String title;
	private String subTitle;
	private PostType postType;
	private String summary;
	private Blog blog;
	private LocalDateTime createdAt;
	private String createdBy;
	private String lastModifiedBy;
	private LocalDateTime lastModifiedAt;
}
