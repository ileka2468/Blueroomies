package edu.depaul.cdm.se452.concept.base.school.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Demonstrate the Junit and Lombok code generation
 */
public class CourseTest {
    @Test
    public void testSetterGetter() {
        Course course = new Course();
        String name = "OO Software Development";
        course.setName(name);
        assertEquals(name, course.getName());
    }

    @Test
    public void testToString() {
        Course course = new Course();
        String name = "OO Software Development";
        course.setName(name);
        String toString = course.toString();
        assertTrue(toString.contains(name));
    }

    @Test
    public void testHashCode() {
        Course course = new Course();
        int b4 = course.hashCode();
        String name = "OO Software Development";
        course.setName(name);
        int after = course.hashCode();
        assertNotEquals(b4, after);
    }    

    @Test
    public void testAllArgConstructor() {
        String name = "OO Software Development";
        Course course = new Course(Long.valueOf(10), "SE", "452", name);
        String toString = course.toString();
        assertTrue(toString.contains(name));
        
    }

    /**
     * Builder allows for chaining of the setters rather than passing all the values for the all argument constructor
     */
    @Test
    public void testBuilder() {
        String name = "OO Software Development";
        Course course = Course.builder().name(name).build();
        String toString = course.toString();
        assertTrue(toString.contains(name));
        
    }

}
