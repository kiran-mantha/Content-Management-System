package com.example.cms.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.dto.BlogRequest;
import com.example.cms.exception.BlogAlreadyExistsByTitleException;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.TopicNotSpecifiedException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.Blog;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;
import com.example.cms.utility.response.BlogResponse;

@Service
public class BlogServiceImpl implements BlogService {

	private BlogRepository br;
	private UserRepository ur;
	private ResponseStructure<BlogResponse> responseStructure;
	
	public BlogServiceImpl(BlogRepository br, UserRepository ur, ResponseStructure<BlogResponse> responseStructure) {
		this.br = br;
		this.ur = ur;
		this.responseStructure = responseStructure;
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(int userId, BlogRequest blogRequest) {
		return ur.findById(userId).map(user->{
			if(br.existsByTitle(blogRequest.getTitle())) throw new BlogAlreadyExistsByTitleException("Blog will not be created");
			if (blogRequest.getTopics().length<1) throw new TopicNotSpecifiedException("Failed to create Blog");
			Blog blog = mapToBlogRequest(blogRequest, new Blog());
			user.getBlogs().add(blog);
			ur.save(user);
			return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
													  .setMessage("Blog is created")
													  .setData(mapToBlogResponse(blog)));
		}).orElseThrow(()->new UserNotFoundByIdException("Failed to create blog"));
	}

	@Override
	public ResponseEntity<Boolean> checkBlogTitleAvailability(String title) {
		return new ResponseEntity<Boolean>(br.existsByTitle(title),HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId) {
		return br.findById(blogId).map(blog->{
			return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
													   .setMessage("Blog is found by Id")
													   .setData(mapToBlogResponse(blog)));
		}).orElseThrow(()->new BlogNotFoundByIdException("Blog not Found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlog(BlogRequest blogRequest, int blogId) {
		return br.findById(blogId).map(blog->{
			
			blog.setTitle(blogRequest.getTitle());
			blog.setTopics(blogRequest.getTopics());
			blog.setSummary(blogRequest.getSummary());
			
			return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
													  .setMessage("Blog updated by Id")
													  .setData(mapToBlogResponse(br.save(blog))));
		}).orElseThrow(()->new BlogNotFoundByIdException("Invalid Blog"));
	}

	private Blog mapToBlogRequest(BlogRequest blogRequest, Blog blog) {
		blog.setTitle(blogRequest.getTitle());
		blog.setTopics(blogRequest.getTopics());
		blog.setSummary(blogRequest.getSummary());
		blog.setUser(blogRequest.getUser());
		return blog;
	}
	
	private BlogResponse mapToBlogResponse(Blog blog) {
		BlogResponse blogRes = new BlogResponse();
		blogRes.setBlogId(blog.getBlogId());
		blogRes.setTitle(blog.getTitle());
		blogRes.setTopics(blog.getTopics());
		blogRes.setSummary(blog.getSummary());
		return blogRes;
	}
}
