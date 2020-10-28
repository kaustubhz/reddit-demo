package com.example.demo.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tbl_post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long postId;

//	@NonNull("Cannot be null")
	private String postName;
	@Nullable
	private String postUrl;
	@Nullable
	@Lob
	private String description;

	private Integer voteCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;

	private Instant createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private Subredddit subreddit;

}
