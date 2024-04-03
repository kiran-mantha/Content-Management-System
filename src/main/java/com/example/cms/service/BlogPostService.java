package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogPostService {

	ResponseEntity<ResponseStructure<BlogPostResponse>> createPost(int blogId, BlogPostRequest blogRequest);

	ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(int postId, BlogPostRequest blogRequest);

	ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId);

	ResponseEntity<ResponseStructure<BlogPostResponse>> publishBlogPost(int postId, PublishRequest publishRequest);

	ResponseEntity<ResponseStructure<BlogPostResponse>> findBlogPostById(int postId);

	ResponseEntity<ResponseStructure<BlogPostResponse>> unpublishBlogPost(int postId);

	ResponseEntity<ResponseStructure<BlogPostResponse>> findBlogPostByIdByPostType(int postId);

}
