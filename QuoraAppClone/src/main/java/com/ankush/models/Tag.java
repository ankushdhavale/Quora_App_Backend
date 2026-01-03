package com.ankush.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Tag extends BaseModel{
		
	private String name;
	
	@ManyToMany(mappedBy = "followedTags")
	private Set<User> followers;
}
