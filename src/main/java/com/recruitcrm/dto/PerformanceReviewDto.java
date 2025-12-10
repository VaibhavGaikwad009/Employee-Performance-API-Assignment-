package com.recruitcrm.dto;

import java.time.LocalDate;

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
public class PerformanceReviewDto {
    private Long id;
    private LocalDate reviewDate;
    private Double score;
    private String reviewComments;
}
