package edu.depaul.cdm.se452.concept.base.school.complex.inheritance;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class HourlyEmployee extends Employee {
    private double rate;
}
