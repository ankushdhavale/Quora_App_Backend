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

import com.ankush.dtos.CommentDto;
import com.ankush.models.Comment;
import com.ankush.services.CommentService;

@RestController
@RequestMapping("/app/v1/comments")
public class CommentController {
	
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@GetMapping("/answer/{answerId}")
	public ResponseEntity<List<Comment>> getCommentsByAnswerId(@PathVariable Long answerId,@RequestParam int page,@RequestParam int size){
		return ResponseEntity.ok(commentService.getCommentByAnswerId(answerId, page, size));
	}
	
	
	@GetMapping("/comment/{commentId}")
	public ResponseEntity<List<Comment>> getRepliesByComment(@PathVariable Long commentId,@RequestParam int page,@RequestParam int size){
		return ResponseEntity.ok(commentService.getRepliseByCommentId(commentId, page, size));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Comment> getCommentById(@PathVariable Long id){
		Comment comment = commentService.getCommentById(id).orElseThrow(()->new RuntimeException("Comment Not Found"));
		return ResponseEntity.ok(comment);
	}
	
	@PostMapping
	public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto){
		Comment comment = commentService.createComment(commentDto);
		return new ResponseEntity(comment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long id){
		commentService.deleteComment(id);
		return ResponseEntity.noContent().build();
	}
}
