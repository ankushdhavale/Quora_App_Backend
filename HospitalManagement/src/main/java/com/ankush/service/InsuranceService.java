package com.ankush.service;

import org.springframework.stereotype.Service;

import com.ankush.entity.Insurance;
import com.ankush.entity.Patient;
import com.ankush.repository.InsuranceRepository;
import com.ankush.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsuranceService {

	private final InsuranceRepository insuranceRepository;
	private final PatientRepository patientRepository;

	@Transactional
	public Patient assignInsuranceToPatient(Insurance insurance, Long patientId) {
		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new EntityNotFoundException("Patient not found.."));

		patient.setInsurance(insurance);
		insurance.setPatient(patient);

		return patient;
	}

	@Transactional
	public Patient disaccociateInsuranceFromPatient(Long patientId) {
		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new EntityNotFoundException("Patient not found.."));
	
		patient.setInsurance(null);
		return patient;
	}
}
