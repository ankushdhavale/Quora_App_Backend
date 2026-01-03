package com.ankush.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.ankush.dto.LoginResponseDto;
import com.ankush.entity.User;
import com.ankush.entity.type.AuthProviderType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthUtil {
	
	
	@Value("${jwt.secretKey}")
	private String jwtSecretKey;
	
	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateAccsesToken(User user) {
		return Jwts.builder()
				.subject(user.getUsername())
				.claim("userId", user.getId().toString())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+1000*60*60))
				.signWith(getSecretKey())
				.compact();
	}
	
	public String getUsernameFromToken(String token) {
	    Claims claims =  Jwts.parser()
	    		.verifyWith(getSecretKey())
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();
	    
	    return claims.getSubject();
	}
	
	public AuthProviderType handleOAuth2LoginRequest(OAuth2User oAuth2User, String registerId) {
		return switch(registerId.toLowerCase()) {
			case "google" -> AuthProviderType.GOOGLE;
			case "github" -> AuthProviderType.GITHUB;
			case "facebook" -> AuthProviderType.FACEBOOK;
			default -> throw new IllegalArgumentException("Unsupported OAuth Provider : "+registerId);
		};
	}
	
	public String determineProviderIdFromOAuth2User(OAuth2User oAuth2User,String registrationId) {
		String providerId = switch(registrationId.toLowerCase()) {
			case "google" -> oAuth2User.getAttribute("sub");
			case "github" -> oAuth2User.getAttribute("id").toString();
			default ->{
				log.error("Unsupported OAuth2 provider : {}",registrationId);
				throw new IllegalArgumentException("Unsupported OAuth2 provider : {}"+registrationId);
			}
		};
		
		if(providerId==null || providerId.isBlank()) {
			log.error("Unabled to determine providerId for provider : {}",registrationId);
			throw new IllegalArgumentException("Unabled to determine providerId for OAuth2 login");
		}
		
		return providerId;
	}
	
	public String determineUsernameFromOAuth2User(OAuth2User oAuth2User,String registerId,String providerId) {
		String email = oAuth2User.getAttribute("email");
		if(email != null && !email.isBlank()) {
			return email;
		}
		
		return switch(registerId.toLowerCase()) {
			case "google" -> oAuth2User.getAttribute("sub");
			case "github" -> oAuth2User.getAttribute("login");
			default -> providerId;
		};
	}
}
