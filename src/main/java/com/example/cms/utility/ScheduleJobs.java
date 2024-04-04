package com.example.cms.utility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.cms.enums.PostType;
import com.example.cms.model.BlogPost;
import com.example.cms.repository.BlogPostRepository;

@Component
public class ScheduleJobs {
	
	@Autowired
	private BlogPostRepository postRepo;

	@Scheduled(fixedDelay = 60*100l)
	public void publishScheduledBlogPosts() {
		
		List<BlogPost> posts = postRepo.findAllByPublish_Schedule_DateTimeLessThanEqualAndPostType(LocalDateTime.now(), PostType.SCHEDULED)
									   .stream()
									   .map(post ->{
											post.setPostType(PostType.PUBLISHED);
											return post;
									   		})
									   .collect(Collectors.toList());
		
		postRepo.saveAll(posts);
	}
	
	@Scheduled(fixedDelay = 1000l)
	public void logDateTime() {
		System.out.println(LocalDateTime.now());
	}
}
