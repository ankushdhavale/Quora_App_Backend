package com.ankush.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankush.dto.DoctorResponseDto;
import com.ankush.service.DoctorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class HospitalController {
	
	private final DoctorService doctorService;
	
	@GetMapping("/doctors")
	public ResponseEntity<List<DoctorResponseDto>> getAllDoctors(){
		return ResponseEntity.ok(doctorService.getAllDoctors());
	}
}
