package com.ankush.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.ankush.entity.type.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	private final HandlerExceptionResolver handlerExceptionResolver; 
	public static final String[] PUBLIC_URLS = {
			"/public/**","/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger_ui/**",
			"/webjars/**"
	};
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrfConfig -> csrfConfig.disable())
				.sessionManagement(
						sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(PUBLIC_URLS).permitAll()
						.requestMatchers("/admin/**").hasRole(RoleType.ADMIN.name())
						.requestMatchers("/doctors/**").hasAnyRole(RoleType.DOCTOR.name(),RoleType.ADMIN.name())				
						.anyRequest()
						.authenticated()
			)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.oauth2Login(oAuth->oAuth
					.failureHandler(
							(request,response,exception)->{
								log.error("oAuth2 error: {} ",exception.getMessage());
								handlerExceptionResolver.resolveException(request, response, null, exception);
							})
					.successHandler(oAuth2SuccessHandler)
			)
			.exceptionHandling(exceptionHandlerConfigurer->
				exceptionHandlerConfigurer.accessDeniedHandler((request,response,exception)->{
					handlerExceptionResolver.resolveException(request, response, null, exception);
				})
			);
		return http.build();
	}

}
