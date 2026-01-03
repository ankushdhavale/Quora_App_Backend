package com.ankush;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ankush.entity.Insurance;
import com.ankush.entity.Patient;
import com.ankush.service.InsuranceService;

@SpringBootTest
public class InsuranceTests {

	
	@Autowired
	private InsuranceService insuranceService;
	
	@Test
	public void testInsurance() {
		Insurance insurance = Insurance.builder()
				.policyNumber("HDFC_1234")
				.provider("HDFC")
				.validUntil(LocalDate.of(2030, 12, 12))
				.build();
		
		Patient patient = insuranceService.assignInsuranceToPatient(insurance, 1L);
		System.out.println(patient);
	
	}
}
