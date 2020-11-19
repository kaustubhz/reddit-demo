package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CommentsDto;
import com.example.demo.exception.PostNotFoundException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;

	public void save(CommentsDto commentsDto) {
//		To extract post by it's unique ID
		Post post = postRepository.findById(commentsDto.getPostId()).orElseThrow(
				() -> new PostNotFoundException("No such post found with ID " + commentsDto.getPostId().toString()));

		/*
		 * Since user has to be logged in, we use authService instead of finding user by
		 * UserRepository
		 */
		User user = authService.getCurrentUser();
		Comment comment = commentMapper.map(commentsDto, post, user);
		commentRepository.save(comment);
	}
}
