package com.recruitcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recruitcrm.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
