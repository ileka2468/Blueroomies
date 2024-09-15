package edu.depaul.cdm.se452.concept.base.school.complex.inheritance;

import java.time.LocalDate;


import lombok.Data;

@Data
public abstract class Employee {
    private Long id;

    private String name;

    private LocalDate startDate;
    
}
