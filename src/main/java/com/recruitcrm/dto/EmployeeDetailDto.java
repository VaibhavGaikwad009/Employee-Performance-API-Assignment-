package com.recruitcrm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDetailDto {

    private Long id;
    private String name;
    private String email;
    private LocalDate dateOfJoining;
    private BigDecimal salary;
    private String managerName;

    private DepartmentDto department;
    private List<ProjectDto> projects;
    private List<PerformanceReviewDto> lastThreeReviews;
}