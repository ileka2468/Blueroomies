package edu.depaul.cdm.se452.concept.base.school.simple;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import edu.depaul.cdm.se452.concept.Repository;

public class CourseRepository implements Repository<Course, Long> {
    private static HashMap<Long, Course> COURSES = new HashMap<Long, Course>();

    @Override
    public Course save(Course course) {
        COURSES.put(course.getId(), course);
        return course;
    }    

    @Override
    public Optional<Course> findById(Long id) {
        Optional<Course> courses = COURSES.entrySet().stream()
            .filter(e -> id.equals(e.getKey()))
            .map(Map.Entry::getValue)
            .findFirst();

        return courses;
    }

    @Override
    public int count() {
        return COURSES.size();
    }
}
