package edu.depaul.cdm.se452.rfa.roomate.service;

import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.profileManagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoommateMatcherServiceTest {
    private RoommateMatcherService roommateMatcherService;
    private MatchStorageService matchStorageService;

    // dummy profiles
    private Profile profile1;
    private Profile profile2;
    private Profile profile3;
    private Profile selectedProfile;

    @BeforeEach
    public void setUp() {
        matchStorageService = Mockito.mock(MatchStorageService.class);
        roommateMatcherService = new RoommateMatcherService(matchStorageService);

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
    void filterByGender() {
        Profile selectedProfile = new Profile();
        selectedProfile.setId(5);
        selectedProfile.setCharacteristics(
                Map.of("cleanliness_level", 3,
                        "gender_preference", "Male",
                        "smoking_preference", false,
                        "alcohol_usage", false

        ));

        List<Profile> profiles = Arrays.asList(profile1, profile2, profile3);
        List<Profile> filteredProfiles = roommateMatcherService.filterByGender(selectedProfile, profiles);
//        System.out.println(filteredProfiles);

        List<Profile> test = new ArrayList<>();
        test.add(filteredProfiles.get(0));
        test.add(filteredProfiles.get(1));

//        System.out.println(test);

        assertEquals(2, test.size());
        assertTrue(test.contains(profile1));
        assertTrue(test.contains(profile2));
        assertFalse(test.contains(profile3));
    }

    @Test
    void filterBySmoking() {
        Profile selectedProfile = new Profile();
        selectedProfile.setId(5);
        selectedProfile.setCharacteristics(
                Map.of("cleanliness_level", 3,
                        "gender_preference", "Male",
                        "smoking_preference", false,
                        "alcohol_usage", false

                ));
        // should just be profile 1
        List<Profile> profiles = Arrays.asList(profile1, profile2, profile3);
        List<Profile> filteredProfiles = roommateMatcherService.filterBySmoking(selectedProfile, profiles);
        List<Profile> test = new ArrayList<>();
        test.add(filteredProfiles.get(0));

        assertEquals(1, test.size());
        assertTrue(test.contains(profile1));
        assertFalse(test.contains(profile3));
        assertFalse(test.contains(profile2));
    }

    @Test
    void filterByDrinking() {
        Profile selectedProfile = new Profile();
        selectedProfile.setId(5);
        selectedProfile.setCharacteristics(
                Map.of("cleanliness_level", 3,
                        "gender_preference", "Male",
                        "smoking_preference", false,
                        "alcohol_usage", false

                ));

        // should just be profile 2
        List<Profile> profiles = Arrays.asList(profile1, profile2, profile3);
        List<Profile> filteredProfiles = roommateMatcherService.filterByDrinking(selectedProfile, profiles);
        List<Profile> test = new ArrayList<>();

        test.add(filteredProfiles.get(0));
        assertEquals(1, test.size());

        assertTrue(test.contains(profile2));
        assertFalse(test.contains(profile3));
        assertFalse(test.contains(profile1));
    }

    @Test
    void filterByCleanlinessTest() {
        Profile selectedProfile = new Profile();
        selectedProfile.setId(5);
        selectedProfile.setCharacteristics(
                Map.of("cleanliness_level", 2,
                        "gender_preference", "Male",
                        "smoking_preference", false,
                        "alcohol_usage", false
                ));

        // should be profile 3
        List<Profile> profiles = Arrays.asList(profile1, profile2, profile3);
        List<Profile> filteredProfiles = roommateMatcherService.filterByCleanliness(selectedProfile, profiles);
        List<Profile> test = new ArrayList<>();
        test.add(filteredProfiles.get(0));
        assertEquals(1, test.size());

        assertTrue(test.contains(profile3));
        assertFalse(test.contains(profile2));
        assertFalse(test.contains(profile1));
    }

    @Test
    void calculateWeightedDistance() {
        Map<String, Object> currentCharacteristics = profile1.getCharacteristics();
        Map<String, Object> preferences = profile2.getCharacteristics();
        List<Profile> compatibleProfiles = Arrays.asList(profile2, profile3);

        double distance = roommateMatcherService.calculateWeightedDistance(currentCharacteristics, preferences, compatibleProfiles);

//        System.out.println("Distance: " + distance);
        assertNotNull(distance, "Distance shouldn't be null.");
    }

    @Test
    void findKNearestNeighborsAndSaveMatch() {
        Profile idealProfile = new Profile();
        idealProfile.setId(6);
        idealProfile.setCharacteristics(
                Map.of("cleanliness_level", 3,
                        "gender_preference", "Male",
                        "smoking_preference", false,
                        "alcohol_usage", false

                ));

        Profile selectedProfile = new Profile();
        selectedProfile.setId(5);
        selectedProfile.setCharacteristics(
                Map.of("cleanliness_level", 3,
                        "gender_preference", "Male",
                        "smoking_preference", false,
                        "alcohol_usage", false

                ));

        List<Profile> profiles = Arrays.asList(idealProfile, profile1, profile2, profile3);
        List<Profile> genderFiltered = roommateMatcherService.filterByGender(selectedProfile, profiles);

        List<Profile> nearestNeighbors = roommateMatcherService.findKNearestNeighbors(selectedProfile, genderFiltered, 3);

        // check if behavior is correct
        assertNotNull(nearestNeighbors, "Nearest neighbors list should not be null");
        assertEquals(3, nearestNeighbors.size(), "There should be 2 nearest neighbors");

        // double check profiles returned
        assertEquals(idealProfile, nearestNeighbors.get(0), "First neighbor should be idealProfile");
        assertEquals(profile1, nearestNeighbors.get(1), "Second neighbor should be profile1");
    }

    @Test
    void findKNearestNeighborsEmptyList() {
        Profile someProfile = new Profile();
        someProfile.setId(3);
        someProfile.setCharacteristics(
                Map.of("cleanliness_level", 3,
                        "gender_preference", "Male",
                        "smoking_preference", false,
                        "alcohol_usage", false

                ));
        List<Profile> blankProfiles = Collections.emptyList();

        List<Profile> blankNeighbors = roommateMatcherService.findKNearestNeighbors(someProfile, blankProfiles, 2);
        assertTrue(blankNeighbors.isEmpty(), "No neighbors should be found when the profiles list is empty");
    }

    @Test
    void testSaveMatch() {
        User u1 = new User();
        u1.setId(1);
        User u2 = new User();
        u2.setId(2);

        double matchScore = 85.7;
        roommateMatcherService.saveMatchToRepo(u1, u2, matchScore);

        List<?> matches = matchStorageService.findAllMatches();
        System.out.println("Matches repo after save: " + matches);

        verify(matchStorageService, times(1)).saveMatch(u1, u2, matchScore);
    }
}
