package com.recruitcrm.controller;


import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recruitcrm.dto.EmployeeDetailDto;
import com.recruitcrm.dto.EmployeeSummaryDto;
import com.recruitcrm.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

 
    @GetMapping
    public List<EmployeeSummaryDto> getEmployees(
            @RequestParam(required = false) Double score,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reviewDate,
            @RequestParam(required = false) List<String> departments,
            @RequestParam(required = false) List<String> projects
    ) {
        return employeeService.getEmployees(score, reviewDate, departments, projects);
    }

   
    @GetMapping("/{id}")
    public EmployeeDetailDto getEmployeeDetail(@PathVariable Long id) {
        return employeeService.getEmployeeDetail(id);
    }
}

