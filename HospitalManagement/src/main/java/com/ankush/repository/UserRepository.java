package com.ankush.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankush.entity.User;
import com.ankush.entity.type.AuthProviderType;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String name);

	Optional<User> findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);
}
