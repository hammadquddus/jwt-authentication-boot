package com.virtualpairprogrammers.roombooking.services;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtService {

	private RSAPrivateKey privateKey;
	private RSAPublicKey publicKey;
	private long expirationTime = 1800000;
	
	@PostConstruct
	private void initKeys() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		
		this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
		this.publicKey = (RSAPublicKey) keyPair.getPublic();
		
	}
	
	public String generateToken(String userName, String role) {
		return JWT.create()
			.withClaim("user",userName)
			.withClaim("role", role)
			.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
			.sign(Algorithm.RSA256(this.publicKey,this.privateKey));
		
	}
	
	public String validateToken(String token) {
		String base64EncodedPayload = JWT.require(Algorithm.RSA256(this.publicKey, this.privateKey))
			.build()
			.verify(token)
			.getPayload();
			
		System.out.println(base64EncodedPayload);
		
		return new String(Base64.getDecoder().decode(base64EncodedPayload));
		
	}
	
	
}
