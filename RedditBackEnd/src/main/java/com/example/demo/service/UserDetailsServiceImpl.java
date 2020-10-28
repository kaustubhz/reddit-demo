package com.example.demo.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository repository;

	@Override
	@Transactional()
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<User> userOptional = repository.findByUserName(userName);
		User user = userOptional
				.orElseThrow(() -> new UsernameNotFoundException("No user found" + "with the username " + userName));
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(),
				user.isEnabled(), true, true, true, getAuthorities("USER"));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}
}
