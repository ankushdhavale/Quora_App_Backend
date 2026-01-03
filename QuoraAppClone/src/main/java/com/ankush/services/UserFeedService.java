package com.ankush.services;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ankush.models.Question;
import com.ankush.models.Tag;
import com.ankush.models.User;
import com.ankush.repositories.QuestionRepository;
import com.ankush.repositories.UserRepository;

@Service
public class UserFeedService {
	
	private UserRepository userRepository;
	private QuestionRepository questionRepository; 
	
	
	public UserFeedService(UserRepository userRepository,QuestionRepository questionRepository) {
		this.userRepository = userRepository;
		this.questionRepository = questionRepository;
	}
	
	public List<Question> getUserFeed(Long userId,int page,int size){
		User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User Id Not Found"));
		
		Set<Long> tagIds = user.getFollowedTags().stream().map(Tag::getId).collect(Collectors.toSet());
		
		return questionRepository.findQuestionByTags(tagIds, PageRequest.of(page,size)).getContent();
	}
	
}
