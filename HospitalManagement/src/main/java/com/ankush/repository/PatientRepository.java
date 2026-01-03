package com.ankush.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ankush.dto.BloodGroupCountResponseEntity;
import com.ankush.entity.Patient;
import com.ankush.entity.type.BloodGroupType;

import jakarta.transaction.Transactional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	Patient findByName(String name);

//	List<Patient> findByBirthDateOrEmail(LocalDate birtDate, String email);
//
//	List<Patient> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);
//
//	List<Patient> findByNameContainingOrderByDesc(String query);

//	@Query("SELECT p FROM Patient p where p.bloodGroup = ?1")
//	List<Patient> findByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroup);

//	@Query("SELECT p from Patient p where p.birthDate > :birthDate")
//	List<Patient> findByBornAfterDate(@Param("birthDate") LocalDate birthDate);

//	@Query("select new com.ankush.dto.BloodGroupCountResponseEntity(p.bloodGroup,"
//			+ "Count(p)) from Patient p group by p.bloodGroup")
//	List<BloodGroupCountResponseEntity> countEachBloodGroupType();

	@Query(value = "select * from patient", nativeQuery = true)
	Page<Patient> findAllPatients(Pageable pageable);

//	@Transactional
//	@Modifying
//	@Query("UPDATE Patient p SET p.name = :name where p.id = :id")
//	int updateNameWithId(@Param("name") String name, @Param("id") Long id);

//	@Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments")
//	List<Patient> findAllPatientWithAppointment();

}
