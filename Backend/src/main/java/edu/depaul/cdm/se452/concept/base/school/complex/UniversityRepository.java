package edu.depaul.cdm.se452.concept.base.school.complex;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import edu.depaul.cdm.se452.concept.Repository;


public class UniversityRepository implements Repository<University, Long> { 
    private static HashMap<Long, University> UNIVERSITY = new HashMap<Long, University>();

    @Override
    public University save(University course) {
        UNIVERSITY.put(course.getId(), course);
        return course;
    }    

    @Override
    public Optional<University> findById(Long id) {
        Optional<University> university = UNIVERSITY.entrySet().stream()
            .filter(e -> id.equals(e.getKey()))
            .map(Map.Entry::getValue)
            .findFirst();

        return university;

    }

    @Override
    public int count() {
        return UNIVERSITY.size();
    }    


    public University findByName(String name) {
        University retval = null;
        for(University university : UNIVERSITY.values()) {
            if (university.getName().equalsIgnoreCase(name)) {
                retval = university;
            }
        }
        return retval;
    }


}