package com.recruitcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recruitcrm.entity.EmployeeProject;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {
}
