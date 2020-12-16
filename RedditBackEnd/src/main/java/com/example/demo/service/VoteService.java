package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.VoteDto;
import com.example.demo.exception.PostNotFoundException;
import com.example.demo.exception.SpringRedditException;
import com.example.demo.model.Post;
import com.example.demo.model.Vote;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.VoteRepository;

import lombok.AllArgsConstructor;

import static com.example.demo.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

	private final PostRepository postRepository;
	private final VoteRepository voteRepository;
	private final AuthService authService;

	@Transactional
	public void vote(VoteDto voteDto) {

		Post findPost = postRepository.findById(voteDto.getPostId()).orElseThrow(
				() -> new PostNotFoundException("No such post with ID" + voteDto.getPostId().toString() + " found"));

		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(findPost,
				authService.getCurrentUser());

		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already" + voteDto.getVoteType() + "'d for this post");
		}

		if (UPVOTE.equals(voteDto.getVoteType())) {
			findPost.setVoteCount(findPost.getVoteCount() + 1);
		} else {
			findPost.setVoteCount(findPost.getVoteCount() - 1);
		}
		voteRepository.save(mapToVote(voteDto, findPost));

		postRepository.save(findPost);
	}

	private Vote mapToVote(VoteDto voteDto, Post findPost) {
		return Vote.builder().post(findPost).voteType(voteDto.getVoteType()).user(authService.getCurrentUser()).build();
	}

}
