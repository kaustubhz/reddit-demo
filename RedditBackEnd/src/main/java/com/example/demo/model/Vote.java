package com.example.demo.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_vote")
@Builder
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long voteId;

	private VoteType voteType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId", referencedColumnName = "postID")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;
}
