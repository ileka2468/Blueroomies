package edu.depaul.cdm.se452.concept.base.school.simple;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import edu.depaul.cdm.se452.concept.Repository;

/**
 * Example of adding additional finders
 */
public class StudentRepository  implements Repository<Student, Long>{
    private static HashMap<Long, Student> STUDENTS= new HashMap<Long, Student>();

    @Override
    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(new Random().nextLong());
        }
        STUDENTS.put(student.getId(), student);
        return student;
    }    

    @Override
    public Optional<Student> findById(Long id) {
        Optional<Student> students = STUDENTS.entrySet().stream()
            .filter(e -> id.equals(e.getKey()))
            .map(Map.Entry::getValue)
            .findFirst(); 
        return students;       
    }

    @Override
    public int count() {
        return STUDENTS.size();
    }
}
