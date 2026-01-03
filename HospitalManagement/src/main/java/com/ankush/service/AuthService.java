package com.ankush.service;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ankush.dto.LoginRequestDto;
import com.ankush.dto.LoginResponseDto;
import com.ankush.dto.SignUpRequestDto;
import com.ankush.dto.SignupResponseDto;
import com.ankush.entity.Patient;
import com.ankush.entity.User;
import com.ankush.entity.type.AuthProviderType;
import com.ankush.entity.type.RoleType;
import com.ankush.repository.PatientRepository;
import com.ankush.repository.UserRepository;
import com.ankush.security.AuthUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PatientRepository patientRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final AuthUtil authUtil;
	private final UserRepository userRepository;

	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
		User user = (User) authentication.getPrincipal();
		String token = authUtil.generateAccsesToken(user);
		return new LoginResponseDto(token, user.getId());
	}

	public User signUpInternal(SignUpRequestDto signupRequestDto,AuthProviderType authProviderType,String providerId) {
		if (userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()) {
		    throw new IllegalArgumentException("User already exists");
		}
		
		User user = User
				.builder()
				.username(signupRequestDto.getUsername())
				.providerId(providerId)
				.providerType(authProviderType)
				.roles(signupRequestDto.getRoles())
				.build();
		
		if(authProviderType == AuthProviderType.EMAIL) {
			user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
		}
		user = userRepository.save(user);
		
		Patient patinet = Patient.builder()
				.name(signupRequestDto.getName())
				.email(signupRequestDto.getUsername())
				.user(user)
				.build();
		
		patientRepository.save(patinet);
		return user;
	}
	
	//login controller
	public SignupResponseDto signup(SignUpRequestDto signupRequestDto) {
		User user = signUpInternal(signupRequestDto,AuthProviderType.EMAIL,null);
		return new SignupResponseDto(user.getId(),user.getUsername());
	}
	
	@Transactional
	public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(
	        OAuth2User oAuth2User,
	        String registrationId) {

	    AuthProviderType providerType =
	            authUtil.handleOAuth2LoginRequest(oAuth2User, registrationId);

	    String providerId =
	            authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

	    // 1️⃣ Try OAuth user
	    User user = userRepository
	            .findByProviderIdAndProviderType(providerId, providerType)
	            .orElse(null);

	    // 2️⃣ Extract email safely
	    String email =
	            authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
	    String name = oAuth2User.getAttribute("name");

	    // 3️⃣ If user does NOT exist → signup
	    if (user == null) {

	        // Check email conflict
	        if (email != null &&
	            userRepository.findByUsername(email).isPresent()) {

	            User existingUser = userRepository.findByUsername(email).get();
	            throw new BadCredentialsException(
	                    "Email already registered with " + existingUser.getProviderType());
	        }

	        user = signUpInternal(
	                new SignUpRequestDto(email, null,name,Set.of(RoleType.PATIENT )),
	                providerType,
	                providerId
	        );
	    }

	    // 4️⃣ Generate JWT
	    String token = authUtil.generateAccsesToken(user);

	    return ResponseEntity.ok(
	            new LoginResponseDto(token, user.getId())
	    );
	}


}
