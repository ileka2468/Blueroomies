package edu.depaul.cdm.se452.concept.base.school.complex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Demonstrate the usage and processing of data structucture 
 */
public class InstructorRepositoryTest {
    private static InstructorRepository repo;

    private static final String TEST_INSTRUCTOR = "Dave";
 
    @BeforeAll
    public static void setup() {        
        repo = new InstructorRepository();
        Instructor i1 = new Instructor();
        i1.setName(TEST_INSTRUCTOR);
        repo.save(i1);

        Instructor i2 = new Instructor();
        i2.setName("Sam");
        repo.save(i2);
    }

    @Test
    public void testFind() {
        assertEquals(2, repo.count());
        
        Optional<Instructor> find = repo.findByName(TEST_INSTRUCTOR);
        assertTrue(find.isPresent());
        Instructor name = find.get();
        assertEquals(name.getName(), TEST_INSTRUCTOR);
    }    

    @Test
    public void testNotFind() {
        assertEquals(2, repo.count());
        
        Optional<Instructor> find = repo.findByName(TEST_INSTRUCTOR + "a");
        assertFalse(find.isPresent());
    }    

}
