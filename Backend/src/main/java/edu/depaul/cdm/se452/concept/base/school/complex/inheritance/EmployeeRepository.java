package edu.depaul.cdm.se452.concept.base.school.complex.inheritance;

import java.util.HashMap;

public class EmployeeRepository<T extends Employee>  {
    private static HashMap<Long, Employee> EMPLOYEES = new HashMap<Long, Employee>();

    public Employee save(Employee course) {
        EMPLOYEES.put(course.getId(), course);
        return course;
    }    

    public Employee findById(Long id) {
        return EMPLOYEES.get(id);
    }

    public Employee findByName(String name) {
        Employee retval = null;
        for(Employee employee : EMPLOYEES.values()) {
            if (employee.getName().equalsIgnoreCase(name)) {
                retval = employee;
            }
        }
        return retval;
    }    
}
