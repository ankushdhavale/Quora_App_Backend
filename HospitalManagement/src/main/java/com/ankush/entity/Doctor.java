package com.ankush.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@MapsId
	private User user;
	
	@Column(nullable = false,length=30)
	private String name;
	 
	@Column(length=100)	
	private String specilization;
	
	@Column(nullable = false,unique = true,length = 30)
	private String email;
	
	@ManyToMany(mappedBy = "doctors")
	private Set<Department> departments = new HashSet<>();
	
	@OneToMany(mappedBy = "doctor")
	private List<Appointment> appoinments = new ArrayList<>();
	
}
