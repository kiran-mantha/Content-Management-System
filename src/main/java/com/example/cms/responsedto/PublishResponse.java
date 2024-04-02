package com.example.cms.responsedto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PublishResponse {

	private int publishId;
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
}
