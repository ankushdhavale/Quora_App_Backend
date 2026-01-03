package com.ankush.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ankush.dto.AppointmentResponseDto;
import com.ankush.dto.CreateAppointmentRequestDto;
import com.ankush.entity.Appointment;
import com.ankush.entity.Doctor;
import com.ankush.entity.Patient;
import com.ankush.repository.AppointmentRepository;
import com.ankush.repository.DoctorRepository;
import com.ankush.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

	private final AppointmentRepository appoinmentRepository;
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;
	private final ModelMapper modelMapper;

	@Transactional
	public AppointmentResponseDto createNewAppoinment(CreateAppointmentRequestDto createAppointmentRequestDto) {
		Long doctorId = createAppointmentRequestDto.getDoctorId();
		Long patientId = createAppointmentRequestDto.getPatientId();

		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new EntityNotFoundException("Patient Not fount with ID : " + patientId));
		Doctor doctor = doctorRepository.findById(doctorId)
				.orElseThrow(() -> new EntityNotFoundException("Doctor Not fount with ID : " + doctorId));

		Appointment appointment = Appointment.builder().reason(createAppointmentRequestDto.getReason())
				.appoinmentTime(createAppointmentRequestDto.getAppointmentTime()).build();

		appointment.setPatient(patient);
		appointment.setDoctor(doctor);
		patient.getAppointments().add(appointment);

		appointment = appoinmentRepository.save(appointment);
		return modelMapper.map(appointment, AppointmentResponseDto.class);
	}

	@Transactional
	public Appointment reAssignAppoinmentToAnotherDoctor(Long appoinmentId, Long doctorId) {
		Appointment appoinment = appoinmentRepository.findById(appoinmentId).orElseThrow();
		Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

		appoinment.setDoctor(doctor);

		doctor.getAppoinments().add(appoinment);

		return appoinment;
	}

	public List<AppointmentResponseDto> getAllAppointmentOfDoctor(Long doctorId) {
		Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

		return doctor.getAppoinments().stream()
				.map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
				.collect(Collectors.toList());
	}

}
