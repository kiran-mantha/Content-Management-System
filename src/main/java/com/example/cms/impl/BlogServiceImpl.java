package com.example.cms.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.exception.BlogAlreadyExistsByTitleException;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.TitleAlreadyExistsException;
import com.example.cms.exception.TopicNotSpecifiedException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.Blog;
import com.example.cms.model.ContributionPanel;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributionPanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

@Service
public class BlogServiceImpl implements BlogService {

	private BlogRepository br;
	private UserRepository ur;
	private ResponseStructure<BlogResponse> responseStructure;
	private ContributionPanelRepository pr;

	public BlogServiceImpl(BlogRepository br, UserRepository ur, ResponseStructure<BlogResponse> responseStructure,
			ContributionPanelRepository pr) {
		this.br = br;
		this.ur = ur;
		this.responseStructure = responseStructure;
		this.pr = pr;
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(int userId, BlogRequest blogRequest) {
		return ur.findById(userId).map(user->{
			
			if(br.existsByTitle(blogRequest.getTitle())) throw new BlogAlreadyExistsByTitleException("Blog will not be created");
			if(blogRequest.getTopics().length<1) throw new TopicNotSpecifiedException("Failed to create Blog");
			
			Blog blog = mapToBlog(blogRequest);
			
			ContributionPanel panel = new ContributionPanel();
			
			panel.getContributors().add(user);
			user.getBlogs().add(blog);
			blog.setUser(user);
			blog.setPanel(panel);
			
			pr.save(panel);
			ur.save(user);
			br.save(blog);
			
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
		
		return br.findById(blogId).map(blog->ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
														   .setMessage("Blog is found by Id")
														   .setData(mapToBlogResponse(blog)))
		).orElseThrow(()->new BlogNotFoundByIdException("Blog not Found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlog(BlogRequest blogRequest, int blogId) {
		
		return br.findById(blogId).map(exBlog->{
			
			if(br.existsByTitle(blogRequest.getTitle())) throw new TitleAlreadyExistsException("Failed to update the blog");
			if(blogRequest.getTopics().length<1) throw new TopicNotSpecifiedException("Failed to update the blog");
			
			Blog blog = mapToBlog(blogRequest);
			
			blog.setBlogId(exBlog.getBlogId());
			
			return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
													  .setMessage("Blog updated by Id")
													  .setData(mapToBlogResponse(br.save(blog))));
		}).orElseThrow(()->new BlogNotFoundByIdException("Failed to update the blog"));
	}

	private Blog mapToBlog(BlogRequest blogRequest) {
		
		return Blog.builder().title(blogRequest.getTitle())
							 .summary(blogRequest.getSummary())
							 .topics(blogRequest.getTopics())
							 .user(blogRequest.getUser())
				   .build();
	}
	
	private BlogResponse mapToBlogResponse(Blog blog) {
		
		return BlogResponse.builder().blogId(blog.getBlogId())
									 .title(blog.getTitle())
									 .topics(blog.getTopics())
									 .summary(blog.getSummary())
						   .build();
	}
}
