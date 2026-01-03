package com.ankush.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.authentication.AuthenticationManager;

import com.ankush.entity.type.BloodGroupType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
		name="patient",
		uniqueConstraints = {
				//@UniqueConstraint(name="unique_patient_email", columnNames = { "email" }),
				@UniqueConstraint(name="unique_patient_name_birthdate",columnNames = { "name","birthDate" })
		},
		indexes= {
				@Index(name="idx_patient_birth_date",columnList = "birthDate")
		}
		)
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@Column(nullable = false,length=40)
	private String name;
	
	private LocalDate birthDate;
	
	@Column(nullable = false,unique = true)
	private String email;
	
	
	private String gender;
	
	@OneToOne
	@MapsId
	private User user;
	
	@CreationTimestamp
	@Column(updatable = false) 
	private LocalDateTime createdAt;
	
	@Enumerated(EnumType.STRING)
	private BloodGroupType bloodGroup;
	                      
	@OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true )
	@JoinColumn(name="patient_insurance_id")//owning side
	private Insurance insurance;
	
	@OneToMany(mappedBy = "patient",cascade = {CascadeType.REMOVE},orphanRemoval = true,fetch=FetchType.EAGER)
	private List<Appointment> appointments = new ArrayList<>();
}
