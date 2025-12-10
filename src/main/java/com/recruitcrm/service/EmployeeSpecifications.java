package com.recruitcrm.service;



import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.recruitcrm.entity.Employee;
import com.recruitcrm.entity.EmployeeProject;
import com.recruitcrm.entity.PerformanceReview;
import com.recruitcrm.entity.Project;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class EmployeeSpecifications {

    public static Specification<Employee> hasPerformanceScoreAndDate(Double score, LocalDate reviewDate) {
        return (root, query, cb) -> {
            if (score == null && reviewDate == null) {
                return cb.conjunction();
            }
            query.distinct(true);
            Join<Employee, PerformanceReview> reviewJoin = root.join("performanceReviews", JoinType.LEFT);

            if (score != null && reviewDate != null) {
                return cb.and(
                        cb.equal(reviewJoin.get("score"), score),
                        cb.equal(reviewJoin.get("reviewDate"), reviewDate)
                );
            } else if (score != null) {
                return cb.equal(reviewJoin.get("score"), score);
            } else {
                return cb.equal(reviewJoin.get("reviewDate"), reviewDate);
            }
        };
    }

    public static Specification<Employee> hasDepartmentNameContainingAny(List<String> departmentNames) {
        return (root, query, cb) -> {
            if (departmentNames == null || departmentNames.isEmpty()) {
                return cb.conjunction();
            }
            query.distinct(true);
            var depJoin = root.join("department", JoinType.LEFT);
            var predicates = departmentNames.stream()
                    .map(name -> cb.like(cb.lower(depJoin.get("name")), "%" + name.toLowerCase() + "%"))
                    .toArray(jakarta.persistence.criteria.Predicate[]::new);
            return cb.or(predicates);
        };
    }

    public static Specification<Employee> hasProjectNameContainingAny(List<String> projectNames) {
        return (root, query, cb) -> {
            if (projectNames == null || projectNames.isEmpty()) {
                return cb.conjunction();
            }
            query.distinct(true);

            Join<Employee, EmployeeProject> epJoin = root.join("employeeProjects", JoinType.LEFT);
            Join<EmployeeProject, Project> projectJoin = epJoin.join("project", JoinType.LEFT);

            var predicates = projectNames.stream()
                    .map(name -> cb.like(cb.lower(projectJoin.get("name")), "%" + name.toLowerCase() + "%"))
                    .toArray(jakarta.persistence.criteria.Predicate[]::new);
            return cb.or(predicates);
        };
    }
}
