package com.ankush.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ankush.dto.DoctorResponseDto;
import com.ankush.dto.OnboardDoctorRequestDto;
import com.ankush.entity.Doctor;
import com.ankush.entity.User;
import com.ankush.entity.type.RoleType;
import com.ankush.repository.DoctorRepository;
import com.ankush.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {
	
	private final DoctorRepository doctorRepository;
	private final ModelMapper modelMapper;
	private final UserRepository userRepository;
	
	public List<DoctorResponseDto> getAllDoctors(){
		return doctorRepository.findAll()
				.stream()
				.map(doctor->modelMapper.map(doctor, DoctorResponseDto.class))
				.collect(Collectors.toList());
	}

	@Transactional
	public DoctorResponseDto onBoardNewDoctor(OnboardDoctorRequestDto onBoardDoctorRequestDto) {
		User user = userRepository.findById(onBoardDoctorRequestDto.getUserId()).orElseThrow();
		
		if(doctorRepository.existsById(onBoardDoctorRequestDto.getUserId())) {
			throw new IllegalArgumentException("Already Doctor Exist.");
		}
		
		Doctor doctor = Doctor.builder()
				.specilization(onBoardDoctorRequestDto.getSpecilization())
				.user(user)
				.build();
		
		user.getRoles().add(RoleType.DOCTOR);
		return modelMapper.map(doctorRepository.save(doctor), DoctorResponseDto.class);
	}
}
