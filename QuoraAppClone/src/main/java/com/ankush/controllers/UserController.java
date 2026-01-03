package com.ankush.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ankush.dtos.UserDto;
import com.ankush.models.Question;
import com.ankush.models.User;
import com.ankush.services.UserFeedService;
import com.ankush.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {	
	
	private UserService userService;
	
	private UserFeedService userFeedService;

	public UserController(UserService userService,UserFeedService userFeedService) {
		this.userService = userService;
		this.userFeedService = userFeedService;
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id){
		Optional<User> user = userService.getUserById(id);
		return user.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public User createUser(@RequestBody UserDto userDto){
		return userService.createUser(userDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		userService.deleteUserById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/{userId}/followTag/{tagId}")
	public ResponseEntity<Void> followTag(@PathVariable Long userId,@PathVariable Long tagId){
		userService.followTag(userId, tagId);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/{id}/feed")
	public ResponseEntity<List<Question>> getUserFeed(@PathVariable Long userId,@RequestParam int page,@RequestParam int size){
		return ResponseEntity.ok(userFeedService.getUserFeed(userId, page, size));
	}
}
