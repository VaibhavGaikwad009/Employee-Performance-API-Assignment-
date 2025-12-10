package com.recruitcrm.service;


import static com.recruitcrm.service.EmployeeSpecifications.hasDepartmentNameContainingAny;
import static com.recruitcrm.service.EmployeeSpecifications.hasPerformanceScoreAndDate;
import static com.recruitcrm.service.EmployeeSpecifications.hasProjectNameContainingAny;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.recruitcrm.dto.DepartmentDto;
import com.recruitcrm.dto.EmployeeDetailDto;
import com.recruitcrm.dto.EmployeeSummaryDto;
import com.recruitcrm.dto.PerformanceReviewDto;
import com.recruitcrm.dto.ProjectDto;
import com.recruitcrm.entity.Employee;
import com.recruitcrm.entity.EmployeeProject;
import com.recruitcrm.entity.PerformanceReview;
import com.recruitcrm.exception.EmployeeNotFoundException;
import com.recruitcrm.repository.EmployeeRepository;
import com.recruitcrm.repository.PerformanceReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PerformanceReviewRepository performanceReviewRepository;

    public List<EmployeeSummaryDto> getEmployees(
            Double score,
            LocalDate reviewDate,
            List<String> departmentNames,
            List<String> projectNames
    ) {
        Specification<Employee> spec = Specification.where(hasPerformanceScoreAndDate(score, reviewDate))
                .and(hasDepartmentNameContainingAny(cleanList(departmentNames)))
                .and(hasProjectNameContainingAny(cleanList(projectNames)));

        List<Employee> employees = employeeRepository.findAll(spec);

        return employees.stream()
                .map(this::toSummaryDto)
                .collect(Collectors.toList());
    }

    public EmployeeDetailDto getEmployeeDetail(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        
        List<PerformanceReview> lastThreeReviews =
                performanceReviewRepository.findTop3ByEmployeeOrderByReviewDateDesc(employee);

        return toDetailDto(employee, lastThreeReviews);
    }

   

    private EmployeeSummaryDto toSummaryDto(Employee e) {
        String deptName = e.getDepartment() != null ? e.getDepartment().getName() : null;

        return EmployeeSummaryDto.builder()
                .id(e.getId())
                .name(e.getName())
                .email(e.getEmail())
                .departmentName(deptName)
                .build();
    }

    private EmployeeDetailDto toDetailDto(Employee e, List<PerformanceReview> lastThreeReviews) {

        
        DepartmentDto departmentDto = null;
        if (e.getDepartment() != null) {
            departmentDto = DepartmentDto.builder()
                    .id(e.getDepartment().getId())
                    .name(e.getDepartment().getName())
                    .budget(e.getDepartment().getBudget())
                    .build();
        }

      
        List<ProjectDto> projectDtos = Optional.ofNullable(e.getEmployeeProjects())
                .orElse(Collections.emptyList())
                .stream()
                .map(EmployeeProject::getProject)
                .filter(Objects::nonNull)
                .map(p -> ProjectDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .startDate(p.getStartDate())
                        .endDate(p.getEndDate())
                        .build())
                .distinct()
                .collect(Collectors.toList());

        
        List<PerformanceReviewDto> reviewDtos = lastThreeReviews.stream()
                .map(r -> PerformanceReviewDto.builder()
                        .id(r.getId())
                        .reviewDate(r.getReviewDate())
                        .score(r.getScore())
                        .reviewComments(r.getReviewComments())
                        .build())
                .collect(Collectors.toList());

        String managerName = e.getManager() != null ? e.getManager().getName() : null;

        return EmployeeDetailDto.builder()
                .id(e.getId())
                .name(e.getName())
                .email(e.getEmail())
                .dateOfJoining(e.getDateOfJoining())
                .salary(e.getSalary())
                .managerName(managerName)
                .department(departmentDto)
                .projects(projectDtos)
                .lastThreeReviews(reviewDtos)
                .build();
    }

    private List<String> cleanList(List<String> raw) {
        if (raw == null) return Collections.emptyList();

        return raw.stream()
                .filter(Objects::nonNull)
                .flatMap(s -> Arrays.stream(s.split(","))) // support comma-separated
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
