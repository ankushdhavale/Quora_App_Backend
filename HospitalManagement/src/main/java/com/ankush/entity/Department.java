package com.ankush.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false,unique = true,length=30)
	private String name;
	
	@OneToOne 
	private Doctor headDoctor;
	
	@ManyToMany
	@JoinTable(
			name="my_dpt_doctors",
			joinColumns=@JoinColumn(name="dpt_id"),
			inverseJoinColumns = @JoinColumn(name="doctor_id")
			)
	private Set<Doctor> doctors = new HashSet<>(); 
}
