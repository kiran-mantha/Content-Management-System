package com.example.cms.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.enums.PostType;
import com.example.cms.model.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {

	Optional<BlogPost> findByPostIdAndPostType(int postId, PostType postType);
	
	List<BlogPost> findAllByPublish_Schedule_DateTime(LocalDateTime dateTime);
	
	List<BlogPost> findAllByPublish_Schedule_DateTimeLessThanEqualAndPostType(LocalDateTime dateTime, PostType postType);
}
