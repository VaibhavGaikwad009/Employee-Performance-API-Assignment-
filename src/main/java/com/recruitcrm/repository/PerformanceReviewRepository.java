package com.recruitcrm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recruitcrm.entity.Employee;
import com.recruitcrm.entity.PerformanceReview;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {

    List<PerformanceReview> findTop3ByEmployeeOrderByReviewDateDesc(Employee employee);
}
