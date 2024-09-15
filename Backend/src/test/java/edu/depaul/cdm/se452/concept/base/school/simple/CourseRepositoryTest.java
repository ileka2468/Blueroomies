package edu.depaul.cdm.se452.concept.base.school.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CourseRepositoryTest {
    @Test
    public void addAndCountTest() {
        Course course = new Course();
        String name = "OO Software Development";
        course.setName(name);
        CourseRepository repo = new CourseRepository();
        repo.save(course);        
        assertEquals(2, repo.count());
    }
    
}
