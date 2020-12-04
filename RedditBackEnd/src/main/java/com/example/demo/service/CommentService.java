package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CommentsDto;
import com.example.demo.exception.PostNotFoundException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.NotificationEmail;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

	private static final String POST_URL = "";
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	/*
	 * Here, mailContentBuilder will create a thymleaf template with particular
	 * message forwarded to build method of this class
	 */
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;

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

		/*
		 * the following return a template which will be used to mail to person's mail
		 * who wrote the post
		 */
		String message = mailContentBuilder
				.build(post.getUser().getUsername() + " posted a comment on your post" + POST_URL);
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(
				new NotificationEmail(user.getUsername() + " commented on your Post", user.getEmail(), message));

	}

	public List<CommentsDto> getAllCommentsForPosts(Long postId) {
		// Let's find a Post by ID
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("No such post found with ID" + postId.toString()));
		return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
	}

	public List<CommentsDto> getAllCommentsForUser(String userName) {
		User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
		return commentRepository.findAllByUser(user)
				.stream()
				.map(commentMapper::mapToDto)
				.collect(Collectors.toList());

	}
}
