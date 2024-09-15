package edu.depaul.cdm.se452.concept.base.school.complex;

import lombok.Data;
import lombok.ToString;

@Data
public class School {
    private Long id;

    private String name;

    private Address address;
    
    @ToString.Exclude
    private University university;   
}
