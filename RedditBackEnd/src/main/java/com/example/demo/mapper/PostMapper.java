package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.model.Post;
import com.example.demo.model.Subreddit;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.VoteRepository;
import com.example.demo.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

	/*
	 * We've made interface to abstract class because we've to manage dependencies
	 * from another class, which cannot be done in interface
	 */
	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private VoteRepository voteRepo;

	@Autowired
	private AuthService authservice;

	/*
	 * By default, MapStruct takes care of mapping PostRequest fields viz subreddit
	 * & user of Model Post Hence, we need not to map these annotations explicitly
	 * 
	 * @Mapping(target = "subreddit", source = "subreddit")
	 * 
	 * @Mapping(target = "user", source = "user")
	 */
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target = "user", source = "user")
//	New fields
	@Mapping(target = "voteCount", constant = "0")
	public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

	/**
	 * Here, subreddit (instance of model Subreddit) is a property in Post model, in
	 * which we invoke it's property viz. name Similar for user field
	 */
	@Mapping(target = "id", source = "postId")
	@Mapping(target = "subredditName", source = "subreddit.name")
	@Mapping(target = "userName", source = "user.username")
	@Mapping(target = "commentCount", expression = "java(commentCount(post))")
	@Mapping(target = "duration", expression = "java(getDuration(post))")
	public abstract PostResponse mapToDto(Post post);

	Integer commentCount(Post post) {
		return commentRepo.findByPost(post).size();
	}

	String getDuration(Post post) {
		return TimeAgo.using(post.getCreatedDate().toEpochMilli());
	}
}
