package com.ankush.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankush.models.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{

}
