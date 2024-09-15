package edu.depaul.cdm.se452.concept.base.school.complex.inheritance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Demonstrate inhertance
 */
public class EmployeeTest {
    @Test
    public void testBasicInheritance() {
        HourlyEmployee hourly = new HourlyEmployee();
        boolean testCase = hourly instanceof Employee;
        assertTrue(testCase);        
    }

    @Test
    public void testEquals() {
        HourlyEmployee hourly = new HourlyEmployee();
        hourly.setName("James");
        hourly.setRate(15.25);
        
        HourlyEmployee hourly2 = new HourlyEmployee();
        hourly2.setName("James");
        hourly2.setRate(15.25);

        assertTrue(hourly.equals(hourly2));
    }

    @Test
    public void testToString() {
        String name = "James";
        HourlyEmployee hourly = new HourlyEmployee();
        hourly.setName(name);
        hourly.setRate(15.25);

        assertFalse(hourly.toString().contains(name), "Hourly does not have callSuper in ToString");
        
        SalaryEmployee salary = new SalaryEmployee();
        salary.setName(name);        

        assertTrue(salary.toString().contains(name), "Salary does have callSuper in ToString");
    }

}
