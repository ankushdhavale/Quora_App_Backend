package com.ankush.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankush.entity.Insurance;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

}
