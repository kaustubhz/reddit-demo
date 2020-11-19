package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.SubredditDto;
import com.example.demo.exception.SpringRedditException;
import com.example.demo.mapper.SubredditMapper;
import com.example.demo.model.Subreddit;
import com.example.demo.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubRedditService {

	private final SubredditRepository repository;
//	Auto wiring Subreddit mapper class which has already defined modelToDto & DtoToModel
	private final SubredditMapper subRedditMapper;

	@Transactional
	public SubredditDto save(SubredditDto sDto) {
		log.info("Inside save subreddit");
		Subreddit save = repository.save(subRedditMapper.mapDtoToSubreddit(sDto));
		sDto.setId(save.getId());
		return sDto;
	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
//		stream() => Returns a sequential Stream with this collection as its source. 
//		Refer to Quip for docs
//		Here, via SubredditMapper interface, we created another way to map model to DTO
		return repository.findAll().stream().map(subRedditMapper::mapSubredditToDto).collect(Collectors.toList());

	}

	public SubredditDto getSubreddit(Long id) {
//		Finding a subreddit by an Id or throwing an exception
		Subreddit subreddit = repository.findById(id)
				.orElseThrow(() -> new SpringRedditException("No such subreddit with ID " + id + " found"));
		return subRedditMapper.mapSubredditToDto(subreddit);
	}

//	The following method no longer required since we've created an alternate way in SubredditMapper
//	private Subreddit mapSubredditDto(SubredditDto sDto) {
//		return Subreddit.builder().name(sDto.getName()).description(sDto.getDescription()).build();
//	}
//
//	private SubredditDto mapToDto(Subreddit subredddit) {
//		 return SubredditDto.builder().id(subredddit.getId())
//				 .name(subredddit.getName()).
//				 description(subredddit.getDescription())
//				 .numberOfPosts(subredddit.getPosts().size())
//				 .build();
//	}

}
