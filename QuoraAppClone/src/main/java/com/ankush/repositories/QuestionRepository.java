package com.ankush.repositories;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ankush.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	@Query("select q from Question q join q.tags t where t.id IN :tagIds")
	Page<Question> findQuestionByTags(Set<Long> tagId,Pageable pageable);
}
