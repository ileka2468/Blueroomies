package edu.depaul.cdm.se452.concept.base.school.complex;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import edu.depaul.cdm.se452.concept.Repository;

public class InstructorRepository implements Repository<Instructor, Long> { 
    private static HashMap<Long, Instructor> INSTRUCTORS = new HashMap<Long, Instructor>();

    @Override
    public Instructor save(Instructor instructor) {
        if (instructor.getId() == null) {
            instructor.setId(new Random().nextLong());
        }
        INSTRUCTORS.put(instructor.getId(), instructor);
        return instructor;
    }    

    @Override
    public Optional<Instructor> findById(Long id) {
        Optional<Instructor> instructor = INSTRUCTORS.entrySet().stream()
            .filter(e -> id.equals(e.getKey()))
            .map(Map.Entry::getValue)
            .findFirst();

        return instructor;
    }

    public Optional<Instructor> findByName(String name) {
        Optional<Instructor> instructors = INSTRUCTORS.values().stream()
            .filter(instructor -> name.equalsIgnoreCase(instructor.getName()))
            .findFirst();

        return instructors;
    }

    @Override
    public int count() {
        return INSTRUCTORS.size();
    }    
}
