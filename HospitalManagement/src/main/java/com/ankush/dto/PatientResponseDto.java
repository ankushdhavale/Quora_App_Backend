package com.ankush.dto;

import java.time.LocalDate;

import com.ankush.entity.type.BloodGroupType;

import lombok.Data;

@Data
public class PatientResponseDto {
	
	private Long id;
	private String name;
	private String gender;
	private LocalDate birthDate;
	private BloodGroupType bloodGroup;
}
