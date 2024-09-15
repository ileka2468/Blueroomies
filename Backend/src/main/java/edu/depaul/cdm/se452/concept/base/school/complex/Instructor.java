package edu.depaul.cdm.se452.concept.base.school.complex;

import java.util.List;

import lombok.Data;
import lombok.ToString.Exclude;

@Data
public class Instructor {
    private Long id;

    private String name;
    
    @Exclude
    private List<University> universities;    
}
