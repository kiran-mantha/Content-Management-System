package com.example.cms.requestdto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class PublishRequest {

	@NotNull(message = "Title should not be empty ")
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
}
