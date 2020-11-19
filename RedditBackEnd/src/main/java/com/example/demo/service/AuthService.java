package com.example.demo.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.exception.SpringRedditException;
import com.example.demo.model.NotificationEmail;
import com.example.demo.model.User;
import com.example.demo.model.VerificationToken;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationTokenRepository;
import com.example.demo.security.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepo;

	private final VerificationTokenRepository verificationTokenRepo;

	private final MailService mailService;

//	The following class has been @Bean in SecurityConfig class
	private final AuthenticationManager authenticationManager;

//	from security package
	private final JwtProvider jwtProvider;

//	As we interact with Relational DB, @Transactional is used
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();

		user.setUsername(registerRequest.getUserName());
//		This will encode password
		user.setPassword(passwordEncoder.encode(registerRequest.getUserPassword()));
		user.setEmail(registerRequest.getUserEmail());
		user.setCreated(Instant.now());
		user.setEnabled(false);

		userRepo.save(user);
		String token = generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail("Please activate your account ", user.getEmail(),
				" Thank you for registering to Spring reddit "
						+ " Please click on the below url to activate your account"
						+ " http://localhost:8080/api/auth/accountVerification/" + token));
	}

	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getUserPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		return new AuthenticationResponse(token, loginRequest.getUserName());
	}

	private String generateVerificationToken(User user) {
//		A random 128 bit token is generated
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);

		verificationTokenRepo.save(verificationToken);

		return token;
	}

	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = verificationTokenRepo.findByToken(token);
		verificationToken.orElseThrow(() -> new SpringRedditException("Invalid verification token"));

		fetchUserAndEnable(verificationToken.get());
	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {

		String userName = verificationToken.getUser().getUsername();
		User user = userRepo.findByUsername(userName)
				.orElseThrow(() -> new SpringRedditException("No such user found with username" + userName));
		user.setEnabled(true);
		userRepo.save(user);
	}

	@Transactional(readOnly = true)
	public User getCurrentUser() {
//		Here, getAuthentication() extracts an authentication request token. 
		org.springframework.security.core.userdetails.User principle = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

//		UsernameNotFoundException is built in exception
		return userRepo.findByUsername(principle.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("Username not found " + principle.getUsername()));
	}

}
