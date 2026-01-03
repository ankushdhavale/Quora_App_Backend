package com.ankush.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankush.dto.AppointmentResponseDto;
import com.ankush.dto.CreateAppointmentRequestDto;
import com.ankush.dto.PatientResponseDto;
import com.ankush.service.AppointmentService;
import com.ankush.service.PatientService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

	private final PatientService patientService;
	private final AppointmentService appointmentService; 
	
	@PostMapping("/appointments")
	public ResponseEntity<AppointmentResponseDto> createNewAppointment(
			@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto) {

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(appointmentService.createNewAppoinment(createAppointmentRequestDto));
	}
	
	@GetMapping("/profile/{id}")
	public ResponseEntity<PatientResponseDto> getPatientProfile(@PathVariable("{id}") Long id){
		return ResponseEntity.ok(patientService.getPatientById(id));
	}
}
