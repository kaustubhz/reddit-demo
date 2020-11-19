package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.service.PostService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor

public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
		postService.save(postRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<PostResponse>> getAllPosts() {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable("id") Long postId) {

		return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(postId));
	}

	@GetMapping("/by-subreddit/{id}")
	public ResponseEntity<List<PostResponse>> getAllPostsBySubreddit(@PathVariable("id") Long subredditId) {

		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(subredditId));
	}

	@GetMapping("/by-user/{name}")
	public ResponseEntity<List<PostResponse>> getAllPostsByUsername(@PathVariable("name") String userName) {

		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(userName));
	}

}
