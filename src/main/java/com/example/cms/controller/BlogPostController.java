package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BlogPostController {

	private BlogPostService blogPostService;
	
	@PostMapping("/blogs/{blogId}/blog-posts")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createPost(@PathVariable int blogId, @RequestBody BlogPostRequest blogRequest) {
		return blogPostService.createPost(blogId, blogRequest);
	}
	
	@PutMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(@PathVariable int postId, @RequestBody BlogPostRequest blogRequest) {
		return blogPostService.updateDraft(postId, blogRequest);
	}
	
	@DeleteMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(@PathVariable int postId) {
		return blogPostService.deleteBlogPost(postId);
	}
	
	@PostMapping("/blog-posts/{postId}/publishes")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> publishBlogPost(@PathVariable int postId, @RequestBody PublishRequest publishRequest) {
		return blogPostService.publishBlogPost(postId, publishRequest);
	}
	
	@PutMapping("/unpublish/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> unpublishBlogPost(@PathVariable int postId) {
		return blogPostService.unpublishBlogPost(postId);
	}
	
	@GetMapping("/blog-posts/{postId}")
	private ResponseEntity<ResponseStructure<BlogPostResponse>> findBlogPostById(@PathVariable int postId) {
		return blogPostService.findBlogPostById(postId);
	}
	
	@GetMapping("/published/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> findBlogPostByIdByPostType(@PathVariable int postId) {
		return blogPostService.findBlogPostByIdByPostType(postId);
	}
}
