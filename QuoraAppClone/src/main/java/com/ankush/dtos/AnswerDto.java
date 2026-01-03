package com.ankush.dtos;

import lombok.Data;

@Data
public class AnswerDto {
	private String content;
	private Long userId;
	private Long questionId;
}
