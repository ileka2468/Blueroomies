package edu.depaul.cdm.se452.concept.base.school.complex.inheritance;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true)
public class SalaryEmployee extends Employee{
    private long salary;    
}
