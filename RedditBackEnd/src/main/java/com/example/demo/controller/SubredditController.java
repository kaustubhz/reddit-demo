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

import com.example.demo.dto.SubredditDto;
import com.example.demo.service.SubRedditService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

	private final SubRedditService subRedditService;

	@PostMapping
	public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto redditDto) {
		log.info("Inside post subreddit");
		return ResponseEntity.status(HttpStatus.CREATED).body(subRedditService.save(redditDto));
	}

	@GetMapping
	public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
		return ResponseEntity.status(HttpStatus.OK).body(subRedditService.getAll());
	}

//	Adding an end point to fetch subreddit by ID
	@GetMapping("/{id}")
	public ResponseEntity<SubredditDto> getSubredditById(@PathVariable Long id) {
		log.info("Inside get subreddit by ID");
		return ResponseEntity.status(HttpStatus.CREATED).body(subRedditService.getSubreddit(id));
	}
}
