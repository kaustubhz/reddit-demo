package com.example.demo.config;

import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.JwtAuthFilter;

import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	For the below interface, we've created an implementation in service package
	private final UserDetailsService userDetailsService;

//	Need to add this before starting an spring boot app
	private final JwtAuthFilter jwtAuthFilter;
//	WebSecurityConfigurerAdapter is a base class for securing our config class as per requirement

//	This method is useful for disable CSRF protection on back end
//	CSRF attacks happens when user is authenticated by session or cookie
//	We only authorize requests from selected endpoint, here it's /api/auth

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/api/auth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/reddit").permitAll().anyRequest().authenticated();
		log.info("Inside configure");
		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	}

//	The following method is useful for authentication of user with JWT
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception {
		managerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

//	Here , @Bean is useful for autowiring 
//	This method will be useful for Password encoding which has PasswordEncoder Interface and BCryptPasswordEncoder is a child class
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	The @Bean is useful to specify specific Beans
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
