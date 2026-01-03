package com.ankush.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Question extends BaseModel{
	
	private String title;
	private String content;
	
	@ManyToMany
	@JoinTable(
			name="question_tags",
			joinColumns = @JoinColumn(name="question_id"),
			inverseJoinColumns = @JoinColumn(name="tag_id")
			)
	private Set<Tag> tags;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
}
