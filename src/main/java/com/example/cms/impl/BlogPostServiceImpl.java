package com.example.cms.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.BlogPost;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributionPanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {

	private BlogPostRepository blogPostRepo;
	private ResponseStructure<BlogPostResponse> responseStructure;
	private BlogRepository blogRepo;
	private UserRepository userRepo;
	private ContributionPanelRepository panelRepo;

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createPost(int blogId, BlogPostRequest blogRequest) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepo.findByEmail(username).map(user -> {
			return blogRepo.findById(blogId).map(blog -> {
				if(!blog.getUser().getEmail().equals(username) && !panelRepo.existsByPanelIdAndContributors(blog.getPanel().getPanelId(), user))
					throw new BlogNotFoundByIdException("Could Not create blog post");
				
				BlogPost blogPost = mapToBlogPostEntity(blogRequest);
				blogPost.setPostType(PostType.DRAFT);
				blogPost.setBlog(blog);
				
				return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
														  .setMessage("Post created")
														  .setData(mapToPostResponse(blogPostRepo.save(blogPost))));
				
			}).orElseThrow(() -> new BlogNotFoundByIdException("Could not create Blog Post"));
		}).orElseThrow(() -> new UserNotFoundByIdException("Could not create Blog Post"));
		
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(int postId, BlogPostRequest blogRequest) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return blogPostRepo.findById(postId).map(exPost -> {
			
			return userRepo.findByEmail(username).map(user -> {
				
				if(!exPost.getBlog().getUser().getEmail().equals(username) && !panelRepo.existsByPanelIdAndContributors(exPost.getBlog().getPanel().getPanelId(), user))
					throw new BlogPostNotFoundByIdException("Cannot Update Blog Post");
				
				BlogPost blogPost = mapToBlogPostEntity(blogRequest);
				blogPost.setPostType(PostType.DRAFT);
				blogPost.setPostId(exPost.getPostId());
				
				return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
														  .setMessage("Post updated successfully")
														  .setData(mapToPostResponse(blogPostRepo.save(blogPost))));
					
				}).orElseThrow(() -> new UsernameNotFoundException("This is not the owner or contributor"));
			}).orElseThrow(() -> new BlogPostNotFoundByIdException("The blog post is not found by given Id: " + postId));
		
	}

	private BlogPostResponse mapToPostResponse(BlogPost post) {
		
		return BlogPostResponse.builder().postId(post.getPostId())
										 .title(post.getTitle())
										 .subTitle(post.getSubTitle())
										 .postType(post.getPostType())
										 .summary(post.getSummary())
										 .createdAt(post.getCreatedAt())
										 .createdBy(post.getCreatedBy())
										 .lastModifiedAt(post.getLastModifiedAt())
										 .lastModifiedBy(post.getLastModifiedBy())
										 .blog(post.getBlog())
							   .build();
	}

	private BlogPost mapToBlogPostEntity(BlogPostRequest blogRequest) {
		
		return BlogPost.builder().title(blogRequest.getTitle())
								 .subTitle(blogRequest.getSubTitle())
								 .summary(blogRequest.getSummary())
					   .build();

	}

}
