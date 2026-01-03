package com.ankush.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ankush.dtos.QuestionDto;
import com.ankush.models.Question;
import com.ankush.services.QuestionService;

@RestController
@RequestMapping("/app/v1/questions")
public class QuestionController {
	
	private QuestionService questionService;

	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	} 
	
	@GetMapping
	public ResponseEntity<List<Question>> getAllQuestion(@RequestParam int page,@RequestParam int size){
		return ResponseEntity.ok(questionService.getQuestions(page, size));
	}
	
	@GetMapping("/id")
	public ResponseEntity<Question> getQuestion(@PathVariable Long id){
		Question question = questionService.getQuestion(id).orElseThrow(()->new RuntimeException("Question Not Found.."));
		return ResponseEntity.ok(question);
	}
	
	@PostMapping
	public ResponseEntity<Question> createQuestion(@RequestBody QuestionDto questionDto){
		Question question = questionService.createQuestion(questionDto);
		return new ResponseEntity(question,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/id")
	public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
		questionService.deleteQuestionById(id);
		return ResponseEntity.noContent().build();
	}
	
}
