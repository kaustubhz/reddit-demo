package com.example.demo.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.SpringRedditException;
import com.example.demo.model.RefreshToken;
import com.example.demo.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken generateRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
//		The following will generate random 128bit figure
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setCreatedDate(Instant.now());

		return refreshTokenRepository.save(refreshToken);
	}

	void validateRefreshToken(String token) {
		refreshTokenRepository.findByToken(token).orElseThrow(
				() -> new SpringRedditException("No such token [ " + token + " ] found, Invalid refresh token"));
	}

	public void deleteToken(String token) {
		refreshTokenRepository.deleteByToken(token);
	}
}
