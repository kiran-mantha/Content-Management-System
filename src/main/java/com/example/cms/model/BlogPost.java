package com.example.cms.model;

import com.example.cms.enums.PostType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BlogPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	@NotNull(message = "title should not be null")
	private String title;
	@Min(value = 500, message = "enter atleast 500 characters")
	@Max(value = 4000, message = "enter maximum of 4000 characters only")
	private String subTitle;
	private String summary;
	private PostType postType;
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
}
