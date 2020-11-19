package com.example.demo.mapper;

import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.model.Post;
import com.example.demo.model.Subreddit;
import com.example.demo.model.User;

@Mapper(componentModel = "spring")
public interface PostMapper {

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
	Post map(PostRequest postRequest, Subreddit subreddit, User user);

	/**
	 * Here, subreddit (instance of model Subreddit) is a property in Post model, in
	 * which we invoke it's property viz. name Similar for user field
	 */
	@Mapping(target = "id", source = "postId")
	@Mapping(target = "subredditName", source = "subreddit.name")
	@Mapping(target = "userName", source = "user.username")
	PostResponse mapToDto(Post post);

}
