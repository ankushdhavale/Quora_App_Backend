package com.ankush.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ankush.dtos.AnswerDto;
import com.ankush.models.Answer;
import com.ankush.models.Question;
import com.ankush.repositories.AnswerRepository;
import com.ankush.repositories.QuestionRepository;
import com.ankush.repositories.UserRepository;

@Service
public class AnswerService {

    private final QuestionService questionService;
		
	private AnswerRepository answerRepository;
	private QuestionRepository questionRepository;
	private UserRepository userRepository;
	
	public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository,
			UserRepository userRepository, QuestionService questionService) {
		this.answerRepository = answerRepository;
		this.questionRepository = questionRepository;
		this.userRepository = userRepository;
		this.questionService = questionService;
	}
	
	public List<Answer> getAnswerByQuestionId(Long questionId,int page,int size){
		return answerRepository.findByQuestionId(questionId, PageRequest.of(page, size)).getContent();
	}
	
	public Optional<Answer> getAnswerById(Long id){
		return answerRepository.findById(id);
	}
	
	public Answer createAnswer(AnswerDto answerDto) {
		Answer answer = new Answer();
		answer.setContent(answerDto.getContent());
		
		Optional<Question> question = questionRepository.findById(answerDto.getQuestionId());
		question.ifPresent(answer::setQuestion);
		
		return answerRepository.save(answer);
	}
	
	
	public void deleteAnswer(Long id) {
		answerRepository.deleteById(id);
	}
}
