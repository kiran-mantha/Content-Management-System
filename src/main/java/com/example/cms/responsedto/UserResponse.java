package com.example.cms.responsedto;

import java.time.LocalDateTime;

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
public class UserResponse {

	private int userId;
	private String username;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	private boolean deleted;
}
