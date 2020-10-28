package com.example.demo.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_verification_token")
@Builder
public class VerificationToken {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long tokenId;
	
	private String token;
	
	@OneToOne(fetch = FetchType.LAZY)
	private User user;
	
	private Instant expiryDate;
}
