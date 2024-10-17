package edu.depaul.cdm.se452.rfa.roomate.service;

import edu.depaul.cdm.se452.rfa.profilemanagement.entity.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
class RoommateMatcherServiceTest {
    private RoommateMatcherService roommateMatcherService;

    // dummy profiles
    private Profile profile1;
    private Profile profile2;
    private Profile profile3;

    @BeforeEach
    public void setUp() {
        RoommateMatcherService matcherService = new RoommateMatcherService();

        profile1 = new Profile();
        profile1.setId(1);
        profile1.setCharacteristics(new HashMap<>(Map.of(
                "cleanliness_level", 4,
                "gender_preference", "Male",
                "smoking_preference", false,
                "alcohol_usage", true
        )));
        profile2 = new Profile();
        profile2.setId(2);
        profile2.setCharacteristics(new HashMap<>(Map.of(
                "cleanliness_level", 5,
                "gender_preference", "Male",
                "smoking_preference", true,
                "alcohol_usage", false
        )));
        profile3 = new Profile();
        profile3.setId(3);
        profile3.setCharacteristics(new HashMap<>(Map.of(
                "cleanliness_level", 3,
                "gender_preference", "Female",
                "smoking_preference", false,
                "alcohol_usage", true
        )));
    }

    @Test
    void calculateWeightedDistance() {
        Map<String, Object> currentCharacteristics = profile1.getCharacteristics();
        Map<String, Object> preferences = profile2.getCharacteristics();
        List<Profile> compatibleProfiles = Arrays.asList(profile2, profile3);

        double distance = roommateMatcherService.calculateWeightedDistance(currentCharacteristics, preferences, compatibleProfiles);

        System.out.println("Distance: " + distance);
        assertNotNull(distance, "Distance shouldn't be null.");
    }

    @Test
    void findKNearestNeighbors() {
        // TODO: NEED TO WRITE OVERRIDE EQUALS and HASHCODE FUNCTION IN PROFILE CLASS
        List<Profile> profiles = Arrays.asList(profile2, profile3);

        List<Profile> nearestNeighbors = roommateMatcherService.findKNearestNeighbors(profile1, profiles, 2);

        // check if behavior is correct
        assertNotNull(nearestNeighbors, "Nearest neighbors list should not be null");
        assertEquals(2, nearestNeighbors.size(), "There should be 2 nearest neighbors");

        // Optionally, verify that the profiles returned are the ones you expect
        assertEquals(profile2, nearestNeighbors.get(0), "First neighbor should be profile2");
        assertEquals(profile3, nearestNeighbors.get(1), "Second neighbor should be profile3");
    }

}
