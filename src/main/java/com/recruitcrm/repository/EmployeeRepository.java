package com.recruitcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.recruitcrm.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>,
JpaSpecificationExecutor<Employee> {
}
