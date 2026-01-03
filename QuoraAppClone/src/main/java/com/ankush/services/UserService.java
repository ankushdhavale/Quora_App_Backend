package com.ankush.services;

import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import com.ankush.dtos.UserDto;
import com.ankush.models.Tag;
import com.ankush.models.User;
import com.ankush.repositories.TagRepository;
import com.ankush.repositories.UserRepository;

@Service
public class UserService {
	

	private UserRepository userRepository;
	
	private TagRepository tagRepository;
	
	public UserService(UserRepository userRepository,TagRepository tagRepository) {
		this.userRepository = userRepository;
		this.tagRepository = tagRepository;
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public Optional<User> getUserById(Long userId) {
		return userRepository.findById(userId);
	}
	
	public User createUser(UserDto userDto) {
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());
		return userRepository.save(user);
	}
	
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}
	
	public void followTag(Long userId,Long tagId) {
		User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User Not Found"));
		Tag tag = tagRepository.findById(tagId).orElseThrow(()->new RuntimeException("Tag Not Found"));
		user.getFollowedTags().add(tag);
		userRepository.save(user);
	}
}
