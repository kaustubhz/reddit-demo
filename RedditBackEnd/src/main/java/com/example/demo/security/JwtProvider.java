package com.example.demo.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.demo.exception.SpringRedditException;

import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {

//	This class represents a storage facility for cryptographickeys and certificates. 
	private KeyStore keyStore;

	@PostConstruct
	public void init() {
		try {
//This method traverses the list of registered security Providers,starting with the most preferred Provider
//			JKS stands for Java Key Store

			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(resourceAsStream, "secret".toCharArray());

		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			throw new SpringRedditException("Exception occured while loading keystore");
		}
	}

	public String generateToken(Authentication authentication) {
//		Here, principle is instance of o.s.security.core, not of model
//		typecast authentication obj to User

		User principle = (User) authentication.getPrincipal();

//		Jwts is a Factory class useful for creating instances of JWT interfaces. 
//Using this factory class can be a good alternative to tightly coupling your code to implementation classes.

		return Jwts.builder().setSubject(principle.getUsername()).signWith(getPrivateKey()).compact();
	}

	private PrivateKey getPrivateKey() {
		try {

			return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {

		}
		return null;
	}

	public void validateToken(String jwtToken) {
		Jwts.parser().setSigningKey(getPublicKey());
	}

	
	/*
	 * java.security.PublicKey
	 * This interface contains no methods or constants. 
	 * merely serves to group (and provide type safety for) all public key interfaces. 
	 */

	private PublicKey getPublicKey() {
		try {
//			Returns the certificate associated with the given alias. Here springblog
			
			return keyStore.getCertificate("springblog").getPublicKey();
		}
		catch (KeyStoreException e) {
			throw new SpringRedditException("Exception occured while retrieving public key");
		}

	}

}
