package com.ankush.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankush.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	Page<Comment> findByAnswerId(Long answerId,Pageable pageable);
	Page<Comment> findByParentCommentId(Long parentCommentId,Pageable pageable);
}
