package com.example.cms.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.UnAuthorizedException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.Blog;
import com.example.cms.model.BlogPost;
import com.example.cms.model.Publish;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributionPanelRepository;
import com.example.cms.repository.PublishRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.responsedto.PublishResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {

	private BlogPostRepository blogPostRepo;
	private BlogRepository blogRepo;
	private UserRepository userRepo;
	private ContributionPanelRepository panelRepo;
	private PublishRepository publishRepo;
	private ResponseStructure<BlogPostResponse> responseStructure;
	private ResponseStructure<PublishResponse> publishStructure;

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

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return blogPostRepo.findById(postId).map(post -> {
			
			return userRepo.findByEmail(username).map(user -> {
				
				if(!post.getBlog().getUser().getEmail().equals(username) && !panelRepo.existsByPanelIdAndContributors(post.getBlog().getPanel().getPanelId(), user))
					throw new UnAuthorizedException("The user do not have access to modify the post");
				
				blogPostRepo.delete(post);
				
				Blog blog = blogRepo.findById(post.getBlog().getBlogId()).get();
				
				blog.getPosts().remove(post);
				
				blogRepo.save(blog);
				
				return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
														  .setMessage("Post deleted successfully")
														  .setData(mapToPostResponse(post)));
				
			}).orElseThrow(() -> new UnAuthorizedException("The user do not have access to modify the post"));
		}).orElseThrow(() -> new BlogPostNotFoundByIdException("The blog post you are searching for is not available"));
	}
	
	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(int postId,
			PublishRequest publishRequest) {
		
		return blogPostRepo.findById(postId).map(post -> {
			
			post.setPostType(PostType.PUBLISHED);
			Publish publish = mapToPublish(publishRequest);
			publish.setBlogPost(post);
			
			return ResponseEntity.ok(publishStructure.setStatuscode(HttpStatus.OK.value())
													  .setMessage("Publish is created Successfully")
													  .setData(mapToPublishResponse(publishRepo.save(publish))));
		}).orElseThrow(() -> new BlogPostNotFoundByIdException("The blog post id you mentioned is no where to be found"));
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
	
	private Publish mapToPublish(PublishRequest publishReq) {
		
		return Publish.builder().seoTitle(publishReq.getSeoTitle())
								.seoDescription(publishReq.getSeoDescription())
								.seoTopics(publishReq.getSeoTopics())
					  .build();
	}
	
	private PublishResponse mapToPublishResponse(Publish publish) {
		
		return PublishResponse.builder().publishId(publish.getPublishId())
										.seoTitle(publish.getSeoTitle())
										.seoDescription(publish.getSeoDescription())
										.seoTopics(publish.getSeoTopics())
							  .build();
	}

}
