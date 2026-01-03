package com.ankush.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankush.entity.Department;
@Repository
public interface DepartmentsRepository extends JpaRepository<Department, Long>{

}
