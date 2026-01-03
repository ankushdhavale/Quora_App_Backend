package com.ankush.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankush.dto.AppointmentResponseDto;
import com.ankush.entity.User;
import com.ankush.service.AppointmentService;
import com.ankush.service.DoctorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
		
//	private final DoctorService doctorService;
	private final AppointmentService appointmentService;
	
	@GetMapping("/appointments")
	public ResponseEntity<List<AppointmentResponseDto>> getAllAppointMentsofDoctor(){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(appointmentService.getAllAppointmentOfDoctor(user.getId()));
	}
}
