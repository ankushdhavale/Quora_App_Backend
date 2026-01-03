package com.ankush.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ankush.dtos.QuestionDto;
import com.ankush.models.Question;
import com.ankush.models.Tag;
import com.ankush.models.User;
import com.ankush.repositories.QuestionRepository;
import com.ankush.repositories.TagRepository;
import com.ankush.repositories.UserRepository;

@Service
public class QuestionService {
	
	private QuestionRepository questionRepository;
	
	private UserRepository userRepository;

	private TagRepository tagRepository;

	public QuestionService(QuestionRepository questionRepository, UserRepository userRepository,
			TagRepository tagRepository) {
		this.questionRepository = questionRepository;
		this.userRepository = userRepository;
		this.tagRepository = tagRepository;
	}
	
	public void deleteQuestionById(Long id) {
		questionRepository.deleteById(id);
	}
	
	public List<Question> getQuestions(int offset,int limit){
		return questionRepository.findAll(PageRequest.of(offset, limit)).getContent();
	}
	
	public Optional<Question> getQuestion(Long id){
		return questionRepository.findById(id);
	}
	
	public Question createQuestion(QuestionDto questionDto) {
		Question question  = new Question();
		question.setTitle(questionDto.getTitle());
		question.setContent(questionDto.getContent());
		question.setId(questionDto.getId());
	
		Optional<User> user = userRepository.findById(questionDto.getUserId());
		user.ifPresent(question::setUser);
		Set<Tag> tags = questionDto
				.getTagIds()
				.stream()
				.map(tagRepository::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(java.util.stream.Collectors.toSet());
		
		question.setTags(tags);
		return questionRepository.save(question);
	}
	
}
