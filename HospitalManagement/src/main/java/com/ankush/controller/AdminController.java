package com.ankush.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ankush.dto.DoctorResponseDto;
import com.ankush.dto.OnboardDoctorRequestDto;
import com.ankush.dto.PatientResponseDto;
import com.ankush.service.DoctorService;
import com.ankush.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

	private final PatientService patientService;
	private final DoctorService doctorService;
	
	@GetMapping("/patients")
	public ResponseEntity<List<PatientResponseDto>> getAllPatient(
			@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
			@RequestParam(value = "size", defaultValue = "10") Integer pageSize) {

		return ResponseEntity.ok(patientService.getAllPatient(pageNumber, pageSize));
	}
	
	@PostMapping("/onBoardNewDoctor")
	public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@RequestBody OnboardDoctorRequestDto onboardDoctorRequestDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(onboardDoctorRequestDto));
	}
}
