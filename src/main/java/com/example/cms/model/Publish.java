package com.example.cms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class Publish {

	@Id
	private int publishId;
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
	
	@OneToOne
	private BlogPost blogPost;
}
