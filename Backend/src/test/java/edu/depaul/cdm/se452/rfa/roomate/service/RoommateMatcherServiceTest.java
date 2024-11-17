package edu.depaul.cdm.se452.rfa.roomate.service;

import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.profileManagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
import edu.depaul.cdm.se452.rfa.roomate.repository.RoommateMatchesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoommateMatcherServiceTest {
    @InjectMocks
    private RoommateMatcherService roommateMatcherService;

    @Mock
    private MatchStorageService matchStorageService;

    @Mock
    private RoommateMatchesRepository mockRepository;

    // dummy profiles
    private Profile profile1;
    private Profile profile2;
    private Profile profile3;
    private Profile selectedProfile;

    @BeforeEach
    public void setUp() {
        // initialize mocks
        MockitoAnnotations.openMocks(this);

        matchStorageService = Mockito.mock(MatchStorageService.class);
        RoommateMatchesRepository matchesRepository = Mockito.mock(RoommateMatchesRepository.class);
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
    void findKNearestNeighbors() {
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
        RoommateMatch match = new RoommateMatch();
        match.setUserId1(u1);
        match.setUserId2(u2);
        match.setMatchScore(BigDecimal.valueOf(matchScore));
        match.setMatchTs(LocalDate.now());

        // configure mock to simulate match saving and retrieval
        when(mockRepository.findAll()).thenReturn(Collections.singletonList(match));
        when(matchStorageService.getMatchesRepository()).thenReturn(mockRepository);

        // call the method to save match
        roommateMatcherService.saveMatchToRepo(u1, u2, matchScore);

        // verify saveMatch was called and check the repository
        verify(matchStorageService, times(1)).addMatch(u1, u2, matchScore);
        assertFalse(mockRepository.findAll().isEmpty(), "Matches repo should not be empty after save.");

        RoommateMatch savedMatch = mockRepository.findAll().get(0);
        System.out.println("Match data: ");
        System.out.println("User ID 1: " + savedMatch.getUserId1());
        System.out.println("User ID 2: " + savedMatch.getUserId2());
        System.out.println("Match score: " + savedMatch.getMatchScore());
        System.out.println("Timestamp: " + savedMatch.getMatchTs());
    }

//    @Test
//    void fullService() {
//        List<RoommateMatch> savedMatches = new ArrayList<>();
//        when(mockMatchRepository.findAll()).thenAnswer(invocation -> savedMatches);
//
//        doAnswer(invocation -> {
//            RoommateMatch match = invocation.getArgument(0);
//            savedMatches.add(match);
//            return match;
//        }).when(mockMatchRepository).save(any(RoommateMatch.class));
//
//        // when(matchStorageService.getMatchesRepository()).thenReturn(mockRepository);
//
//        User user1 = new User();
//        user1.setId(11);
//        User user2 = new User();
//        user2.setId(12);
//        User user3 = new User();
//        user3.setId(13);
//        User user4 = new User();
//        user4.setId(14);
//        User user5 = new User();
//        user5.setId(15);
//        User user6 = new User();
//        user6.setId(16);
//
//        Profile selectedProfile = new Profile();
//        selectedProfile.setUser(user1);
//        System.out.println("user id for selected profile: " + selectedProfile.getUser().getId());
//        selectedProfile.setId(10);
//        selectedProfile.setCharacteristics(
//                Map.of("cleanliness_level", 3,
//                        "gender_preference", "Male",
//                        "smoking_preference", false,
//                        "alcohol_usage", false
//                ));
//
//        Profile profileA = new Profile();
//        profileA.setUser(user2);
//        profileA.setId(1);
//        profileA.setCharacteristics(
//                Map.of("cleanliness_level", 4,
//                        "gender_preference", "Male",
//                        "smoking_preference", false,
//                        "alcohol_usage", true
//                ));
//
//        Profile profileB = new Profile();
//        profileB.setUser(user3);
//        profileB.setId(2);
//        profileB.setCharacteristics(
//                Map.of("cleanliness_level", 5,
//                        "gender_preference", "Male",
//                        "smoking_preference", true,
//                        "alcohol_usage", false
//                ));
//
//        Profile profileC = new Profile();
//        profileC.setUser(user4);
//        profileC.setId(3);
//        profileC.setCharacteristics(
//                Map.of("cleanliness_level", 3,
//                        "gender_preference", "Male",
//                        "smoking_preference", false,
//                        "alcohol_usage", false
//                ));
//
//        Profile profileD = new Profile();
//        profileD.setUser(user5);
//        profileD.setId(4);
//        profileD.setCharacteristics(
//                Map.of("cleanliness_level", 2,
//                        "gender_preference", "Male",
//                        "smoking_preference", false,
//                        "alcohol_usage", false
//                ));
//
//        Profile profileE = new Profile();
//        profileE.setUser(user6);
//        profileE.setId(7);
//        profileE.setCharacteristics(
//                Map.of("cleanliness_level", 3,
//                        "gender_preference", "Male",
//                        "smoking_preference", false,
//                        "alcohol_usage", false
//
//                ));
//
//        // list of profiles to filter an apply knn
//        List<Profile> profiles = Arrays.asList(profileA, profileB, profileC, profileD, profileE);
//
//        // apply filters
//        List<Profile> filteredProfiles = roommateMatcherService.applyFilters(selectedProfile, profiles);
//        System.out.println("Filtered profiles: " + filteredProfiles);
//        System.out.println("Filtered profile count: " + filteredProfiles.size());
//        assertFalse(filteredProfiles.isEmpty(), "Filtered profiles list should not be empty");
//
//        // find KNN
//        int k = 1;
//        List<Profile> nearestNeighbors = roommateMatcherService.findKNearestNeighbors(selectedProfile, filteredProfiles, k);
//        int size = nearestNeighbors.size();
//        System.out.println("k: " + k);
//        System.out.println("nearest neighbors size: " + size);
//        System.out.println("Detailed Nearest Neighbors List:");
//        for (Profile neighbor : nearestNeighbors) {
//            System.out.println("Profile ID: " + neighbor.getId());
//            neighbor.getCharacteristics().forEach((key, value) ->
//                    System.out.println("  " + key + ": " + value)
//            );
//        }
//        assertNotNull(nearestNeighbors, "Nearest neighbors list should not be null");
//        assertEquals(k, size, "Size of nearestNeighbors should be equal to k.");
//
////        User user1 = new User();
////        user1.setId(selectedProfile.getId());
//        RoommateMatch match = new RoommateMatch();
//
//        // add match to matches repository
//        for (Profile neighbor: nearestNeighbors) {
//            User matchUser = new User();
//            matchUser.setId(neighbor.getId());
//
//            double matchScore = roommateMatcherService.calculateWeightedDistance(
//                    selectedProfile.getCharacteristics(),
//                    neighbor.getCharacteristics(),
//                    profiles
//            );
//            match.setUserId1(user1);
//            match.setUserId2(matchUser);
//            match.setMatchScore(BigDecimal.valueOf(matchScore));
//            match.setMatchTs(LocalDate.now());
//
//            roommateMatcherService.saveMatchToRepo(user1, matchUser, matchScore);
//
//            savedMatches.add(match);
//        }
//
//        when(mockMatchRepository.findAll()).thenReturn(Collections.singletonList(match));
//
//        // verify saveMatch was called the correct number of times
//        verify(matchStorageService, times(2)).addMatch(any(User.class), any(User.class), anyDouble());
//
//        // print repository content to verify matches were saved
//        List<RoommateMatch> savedMockMatches = mockMatchRepository.findAll();
//        int sizeSavedMatches = savedMockMatches.size();
//        System.out.println("Saved Match:");
//        for (RoommateMatch singleMatch : savedMockMatches) {
//            System.out.println("User ID 1: " + singleMatch.getUserId1().getId() + "\n" +
//                    "User ID 2: " + singleMatch.getUserId2().getId() + "\n" +
//                    "Match Score: " + singleMatch.getMatchScore() + "\n" +
//                    "Match Time: " + singleMatch.getMatchTs());
//        }
//
//        when(mockMatchRepository.findByUserId(11)).thenReturn(savedMatches);
//
//        // Verify that saveMatch was called for each neighbor
//        verify(matchStorageService, times(2)).addMatch(any(User.class), any(User.class), anyDouble());
//
//        // additional assertion to ensure matches are saved correctly
//        assertEquals(k, sizeSavedMatches, "Number of saved matches should equal K value");
//
//    }

//
//    @Test
//    void testRoommateMatchDTO() {
//        // Create users
//        User user1 = new User();
//        user1.setId(11);
//
//        User user2 = new User();
//        user2.setId(12);
//
//        User user3 = new User();
//        user3.setId(13);
//
//        // Create mock profiles with distinct IDs and characteristics
//        Profile mockProfile1 = new Profile();
//        mockProfile1.setUser(user1);
//        mockProfile1.setCharacteristics(Map.of(
//                "cleanliness_level", 3,
//                "gender_preference", "Male",
//                "smoking_preference", false,
//                "alcohol_usage", false
//        ));
//
//        Profile mockProfile2 = new Profile();
//        mockProfile2.setUser(user2);
//        mockProfile2.setCharacteristics(Map.of(
//                "cleanliness_level", 3,
//                "gender_preference", "Male",
//                "smoking_preference", false,
//                "alcohol_usage", false
//        ));
//
//        Profile mockProfile3 = new Profile();
//        mockProfile3.setUser(user3);
//        mockProfile3.setCharacteristics(Map.of(
//                "cleanliness_level", 4,
//                "gender_preference", "Female",
//                "smoking_preference", true,
//                "alcohol_usage", true
//        ));
//
//
//        // Mock profile service responses
//        when(profileService.getProfileByUserId(user2.getId())).thenReturn(mockProfile2);
//        when(profileService.getProfileByUserId(user3.getId())).thenReturn(mockProfile3);
//
//        // Create matches
//        RoommateMatch match1 = new RoommateMatch();
//        match1.setUserId1(user1);
//        match1.setUserId2(user2);
//        match1.setMatchScore(BigDecimal.valueOf(30));
//        match1.setMatchTs(LocalDate.now());
//
//        RoommateMatch match2 = new RoommateMatch();
//        match2.setUserId1(user1);
//        match2.setUserId2(user3);
//        match2.setMatchScore(BigDecimal.valueOf(40));
//        match2.setMatchTs(LocalDate.now());
//
//        roommateMatcherService.saveMatchToRepo(user1, user2, 30);
//        roommateMatcherService.saveMatchToRepo(user2, user3, 40);
//
//        List<RoommateMatch> matches = List.of(match1, match2);
//
//        // Mock repository response
//        when(mockMatchRepository.findByUserId(user1.getId())).thenReturn(matches);
//
//        // Call the method under test
//        String response = roommateMatcherService.findMatchesForUser(user1.getId());
//
//        // Print and verify the response
//        System.out.println(response);
//
//        // Assertions to check if the response contains expected data
//        assertNotNull(response);
//        assertTrue(response.contains("\"user_id\": 2"));
//        assertTrue(response.contains("\"user_id\": 3"));
//        assertTrue(response.contains("\"cleanliness_level\": 3"));
//        assertTrue(response.contains("\"cleanliness_level\": 4"));
//    }
//


}
