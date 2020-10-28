package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

//	This method will be useful for scanning HTTP header and extracting JWT token
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = getJwtRequest(request);

	}

	private String getJwtRequest(HttpServletRequest request) {

		String bearerToken = request.getHeader("Authorization");

		/**
		 * *org.springframework.util.StringUtils* simple functionality that are provided
		 * by the core Java String and StringBuilder classes.
		 */
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return bearerToken;

	}

}
