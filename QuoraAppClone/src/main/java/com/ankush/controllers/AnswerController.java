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

import com.ankush.dtos.AnswerDto;
import com.ankush.models.Answer;
import com.ankush.services.AnswerService;

@RestController
@RequestMapping("/app/v1/answers")
public class AnswerController {

	private AnswerService answerService;

	public AnswerController(AnswerService answerService) {
		this.answerService = answerService;
	}

	@GetMapping("/question/{questionId}")
	public ResponseEntity<List<Answer>> getAllAnswerByQuestionId(@PathVariable Long questionId, @RequestParam int page,
			@RequestParam int size) {
		return ResponseEntity.ok(answerService.getAnswerByQuestionId(questionId, page, size));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Answer> getAnswerById(@PathVariable Long id) {
		Answer answer = answerService.getAnswerById(id).orElseThrow(() -> new RuntimeException("Answer Not Found"));
		return ResponseEntity.ok(answer);
	}

	@PostMapping
	public ResponseEntity<Answer> createAnswer(@RequestBody AnswerDto asnwerDto) {
		Answer answer = answerService.createAnswer(asnwerDto);
		return new ResponseEntity(answer, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
		answerService.deleteAnswer(id);
		return ResponseEntity.noContent().build();
	}
}
