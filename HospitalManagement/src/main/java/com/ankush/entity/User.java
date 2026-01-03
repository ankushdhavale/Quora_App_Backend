package com.ankush.entity;

import java.security.Permission;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ankush.entity.type.AuthProviderType;
import com.ankush.entity.type.RoleType;
import com.ankush.security.RolePermissionMapping;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="app_user")
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(unique = true,nullable = false)
	private String username;	
	private String password;
	
	private String providerId;
	
	@Enumerated(EnumType.STRING)
	private AuthProviderType providerType;
	
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	Set<RoleType> roles = new HashSet<>();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return roles.stream()
//				.map(role ->new SimpleGrantedAuthority("ROLE_"+role.name()))
//				.collect(Collectors.toSet());
		
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		roles.forEach(role->{
			Set<SimpleGrantedAuthority> permission = RolePermissionMapping.getAuthoritiesForRole(role);
			authorities.addAll(permission);
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
		});
		return authorities;
	}
}
