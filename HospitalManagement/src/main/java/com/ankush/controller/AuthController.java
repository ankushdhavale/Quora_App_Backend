package com.ankush.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankush.dto.LoginRequestDto;
import com.ankush.dto.LoginResponseDto;
import com.ankush.dto.SignUpRequestDto;
import com.ankush.dto.SignupResponseDto;
import com.ankush.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto LoginRequestDto){
		 return ResponseEntity.ok(authService.login(LoginRequestDto));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<SignupResponseDto> signup(@RequestBody SignUpRequestDto signupRequestDto){
		return ResponseEntity.ok(authService.signup(signupRequestDto));
	}
	
}
