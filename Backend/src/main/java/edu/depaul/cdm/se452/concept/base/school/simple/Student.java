package edu.depaul.cdm.se452.concept.base.school.simple;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Additional setup with mapping between code and table/columns when they do not match  class/property
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Long id;
	
	private String studentId;

	private String name;

	private String email;

	private long age;
	private Date admittedDate;
}