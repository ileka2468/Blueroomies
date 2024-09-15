package edu.depaul.cdm.se452.concept.base.school.complex;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import edu.depaul.cdm.se452.concept.Repository;


public class SchoolRepository  implements Repository<School, Long>{ 
    private static HashMap<Long, School> SCHOOLS = new HashMap<Long, School>();

    @Override
    public School save(School school) {
        if (school.getId() == null) {
            school.setId(new Random().nextLong());
        }        
        SCHOOLS.put(school.getId(), school);
        return school;
    }    

    @Override
    public Optional<School> findById(Long id) {
        Optional<School> school = SCHOOLS.entrySet().stream()
            .filter(e -> id.equals(e.getKey()))
            .map(Map.Entry::getValue)
            .findFirst();

        return school;

    }

    @Override
    public int count() {
        return SCHOOLS.size();
    }    

    public School findByName(String name) {
        School retval = null;
        for(School school : SCHOOLS.values()) {
            if (school.getName().equalsIgnoreCase(name)) {
                retval = school;
            }
        }
        return retval;
    }

}