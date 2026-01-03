package com.ankush.dtos;

import java.util.Set;

import lombok.Data;

@Data
public class QuestionDto {
	
	private Long id;
	
	private String title;
	private String content;
	private Long userId;
	private Set<Long> tagIds;
}
