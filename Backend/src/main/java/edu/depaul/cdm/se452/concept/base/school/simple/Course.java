package edu.depaul.cdm.se452.concept.base.school.simple;

import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Minimialist code to map against database when then table/column is same as the class/property
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @Builder.Default
    private Long id = new Random().nextLong();

    private String dept;

    private String num;

    private String name;
}