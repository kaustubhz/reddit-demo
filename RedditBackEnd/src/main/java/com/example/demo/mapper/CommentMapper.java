package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.CommentsDto;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	/*
	 * At this time, I've no idea whether to take post and user as arguments
	 * 
	 * @Mapping(target = "user.username", source = "userName")
	 * 
	 * @Mapping(target = "post", source = "postId")
	 */

	/*
	 * It is necessary to mention full package name, as MapStruct implementation of
	 * this class never imports package on its own
	 */

	// Ignoring id because it is auto generated
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "text", source = "commentsDto.text")
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "post", source = "post")
	Comment map(CommentsDto commentsDto, Post post, User user);

	@Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
	@Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
	CommentsDto mapToDto(Comment comment);
}
