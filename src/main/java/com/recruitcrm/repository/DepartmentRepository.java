package com.recruitcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recruitcrm.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
