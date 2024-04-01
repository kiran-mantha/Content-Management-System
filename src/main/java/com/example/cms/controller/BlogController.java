package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.responsedto.ContributionPanelResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

@RestController
public class BlogController {

	private BlogService bs;

	public BlogController(BlogService bs) {
		this.bs = bs;
	}
	
	@PostMapping("/user/{userId}/blogs")
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(@PathVariable int userId, @RequestBody BlogRequest blogRequest) {
		return bs.createBlog(userId, blogRequest);
	}
	
	@GetMapping("/title/{title}/blogs")
	public ResponseEntity<Boolean> checkBlogTitleAvailability(@PathVariable String title) {
		return bs.checkBlogTitleAvailability(title);
	}
	
	@GetMapping("/blog/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(@PathVariable int blogId) {
		return bs.findBlogById(blogId);
	}
	
	@PutMapping("/blog/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlog(@RequestBody BlogRequest blogRequest, @PathVariable int blogId) {
		return bs.updateBlog(blogRequest, blogId);
	}
	
	@PutMapping("/users/{userId}/contributionPanel/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> addContribution(@PathVariable int userId) {
		return null;
	}
	
}
