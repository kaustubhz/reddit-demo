package com.example.demo.service;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.SubredditDto;
import com.example.demo.model.Subredddit;
import com.example.demo.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubRedditService {

	private final SubredditRepository repository;

	@Transactional
	public SubredditDto save(SubredditDto sDto) {
		log.info("Inside save subreddit");
		Subredddit save = repository.save(mapSubredditDto(sDto));
		sDto.setId(save.getId());
		return sDto;
	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
//		stream() => Returns a sequential Stream with this collection as its source. 
//		Refer to Quip for docs
		return repository.findAll().stream()
		.map(this::mapToDto)
		.collect(Collectors.toList());
		
	}
	
	private Subredddit mapSubredditDto(SubredditDto sDto) {
		return Subredddit.builder().name(sDto.getName()).description(sDto.getDescription()).build();
	}

	private SubredditDto mapToDto(Subredddit subredddit) {
		 return SubredditDto.builder().id(subredddit.getId())
				 .name(subredddit.getName()).
				 description(subredddit.getDescription())
				 .numberOfPosts(subredddit.getPosts().size())
				 .build();
	}
	
}
