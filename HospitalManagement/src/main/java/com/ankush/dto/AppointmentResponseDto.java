package com.ankush.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AppointmentResponseDto {
		
	private Long id;
	private LocalDateTime appoinmentTime;
	private String reason;
	private DoctorResponseDto doctor;
}
