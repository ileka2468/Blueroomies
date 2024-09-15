package edu.depaul.cdm.se452.concept.base.school.complex;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
public class University {
    private long id;

    private String name;
    
    private List<School> schools;    

    @ToString.Exclude
    private List<Instructor> instructors;    
}
