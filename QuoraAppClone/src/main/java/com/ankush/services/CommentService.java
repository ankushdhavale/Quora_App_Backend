package com.ankush.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ankush.dtos.CommentDto;
import com.ankush.models.Answer;
import com.ankush.models.Comment;
import com.ankush.repositories.AnswerRepository;
import com.ankush.repositories.CommentRepository;

@Service
public class CommentService {
	

	private CommentRepository commentRepository;	

	private AnswerRepository answerRepository;
	
	public CommentService(CommentRepository commentRepository, AnswerRepository answerRepository) {
		this.commentRepository = commentRepository;
		this.answerRepository = answerRepository;
	}

	public List<Comment> getCommentByAnswerId(Long answerId,int page , int size){
		return commentRepository.findByAnswerId(answerId, PageRequest.of(page,size)).getContent();
	}
	 
	public List<Comment> getRepliseByCommentId(Long commentId,int page,int size){
		return commentRepository.findByParentCommentId(commentId, PageRequest.of(page, size)).getContent();
	}
	
	public Optional<Comment> getCommentById(Long id){
		return commentRepository.findById(id);
	}
	
	public Comment createComment(CommentDto commentDto) {
		Comment comment = new Comment();
		comment.setId(commentDto.getId());
		comment.setContent(commentDto.getContent());
		
		Optional<Answer> answer = answerRepository.findById(commentDto.getAnswerId());
		answer.ifPresent(comment::setAnswer);
		
		if(commentDto.getParentCommentId()!=null) {
			Optional<Comment> parentComment = commentRepository.findById(commentDto.getParentCommentId());
			parentComment.ifPresent(comment::setParentComment);
		}
		
		return commentRepository.save(comment);
	}
	
	public void deleteComment(Long id) {
		commentRepository.deleteById(id);
	}
}
