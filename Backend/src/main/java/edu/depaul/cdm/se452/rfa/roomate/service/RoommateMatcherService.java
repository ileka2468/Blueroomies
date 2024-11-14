package edu.depaul.cdm.se452.rfa.roomate.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.profileManagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.profileManagement.service.ProfileService;
import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
import edu.depaul.cdm.se452.rfa.roomate.repository.RoommateMatchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The Roommate Matcher Service is a service class for the spring framework that implements the
 * matching system for users (or profiles) based on their characteristics and preferences for potential
 * roommates. It calculates similarity utilizing a modified K-Nearest-Neighbors (KNN) algorithm.
 * <p>
 * The service also filters profiles by gender, smoking/drinking, and cleanliness preferences in order to
 * help find the most compatible roommates.
 */

@Service
public class RoommateMatcherService {

    private final RoommateMatchesRepository roommateMatchesRepository;
    private double[] weights;
    private final MatchStorageService matchStorageService;

    @Autowired
    private ProfileService profileService;

    /**
     * Constructor takes in the MatchStorageService as a parameter.
     *
     * @param matchStorageService   Service for storing matches.
     */
    @Autowired
    public RoommateMatcherService(MatchStorageService matchStorageService, RoommateMatchesRepository roommateMatchesRepository) {
        this.matchStorageService = matchStorageService;
        this.roommateMatchesRepository = roommateMatchesRepository;
    }

    /**
     * Helper method for grabbing characteristics given a profile.
     *
     * @param selectedProfile   target profile to select characteristics from.
     * @return                  characteristics.
     */
    private Map<String, Object> getCharacteristicsFromProfile(Profile selectedProfile){
        return selectedProfile.getCharacteristics();
    }

    /*
     *
     * pre-filtering step before applying KNN
     *
     * each filter takes in a list of profiles parameter derived from the previous filter
     *
     */

    private Optional<Profile> getMatchedProfile(int userId) {
        return profileService.getProfileById(userId);
    }

    /**
     * Private method for grabbing the match DTO.
     * @param match     RoommateMatch object
     * @return          Match DTO.
     */
    private MatchDetailsDTO convertToMatchDetailsDTO(RoommateMatch match){
        ProfilesDTO profileDTO = new ProfilesDTO(
            match.getUserId2().getFirstName() + " " + match.getUserId2().getLastName(),
            "default",
            getMatchedProfile(match.getUserId2().getId()).get().getCharacteristics()
        );

        return new MatchDetailsDTO(match.getUserId1().getId(), match.getMatchScore(), profileDTO);
    }

    /**
     * Protected method for checking gender compatibilities between two profiles.
     * This method checks if the "gender_preference" characteristic matches.
     *
     * @param selectedProfile       the current authenticated user's profile.
     * @param comparingProfile      the next profile to compare against.
     * @return                      true if compatible, false if not.
     */
    private boolean isGenderCompatible(Profile selectedProfile, Profile comparingProfile) {
        Map<String, Object> selectedProfileCharacteristics = getCharacteristicsFromProfile(selectedProfile);
        String selectedGender = (String) selectedProfileCharacteristics.get("gender_preference");
        Map<String, Object> comparingProfileCharacteristics = getCharacteristicsFromProfile(comparingProfile);
        String comparingGender = (String) comparingProfileCharacteristics.get("gender_preference");

        return selectedGender.equals(comparingGender);
    }

    /**
     * Returns a boolean whether two profiles are compatible based on their smoking habit.
     * This method checks if the "smoking_preference" characteristic matches.
     * <p>
     * FIXME ASSUMPTION: Does not take into account if a non-smoker is ok with rooming with a smoker.
     *
     * @param selectedProfile       the current authenticated user's profile.
     * @param comparingProfile      the next profile to compare against.
     * @return                      true if compatible, false if not.
     */
    private boolean isSmokingCompatible(Profile selectedProfile, Profile comparingProfile) {
        Map<String, Object> selectedProfileCharacteristics = getCharacteristicsFromProfile(selectedProfile);
        Boolean selectedSmoking = (Boolean) selectedProfileCharacteristics.get("smoking_preference");
        Map<String, Object> comparingProfileCharacteristics = getCharacteristicsFromProfile(comparingProfile);
        Boolean comparingSmoking = (Boolean) comparingProfileCharacteristics.get("smoking_preference");

        return selectedSmoking.equals(comparingSmoking);
    }

    /**
     * Returns a boolean whether two profiles are compatible based on their alcohol usage.
     * A user should be able to room with someone with differing alcohol usage, but needs to follow
     * a default value. For now, the default is that you cannot room with someone with different usages.
     * <p>
     * FIXME ASSUMPTION: Does not take into account of people rooming with different alcohol usage.
     *
     * @param selectedProfile       the current authenticated user's profile.
     * @param comparingProfile      the next profile to compare against.
     * @return                      true if compatible, false if not.
     */
    private boolean isDrinkingCompatible(Profile selectedProfile, Profile comparingProfile) {
        Map<String, Object> selectedProfileCharacteristics = getCharacteristicsFromProfile(selectedProfile);
        Boolean selectedDrinking = (Boolean) selectedProfileCharacteristics.get("alcohol_usage");
        Map<String, Object> comparingProfileCharacteristics = getCharacteristicsFromProfile(comparingProfile);
        Boolean comparingDrinking = (Boolean) comparingProfileCharacteristics.get("alcohol_usage");

        return selectedDrinking.equals(comparingDrinking);
    }

    /**
     * Returns a boolean whether two profiles are compatible based on their cleanliness level.
     * To allow flexibility, there will be a tolerance of 1 level to avoid bottleneck-ing the pool of potential
     * matches.
     *
     * @param selectedProfile       the current authenticated user's profile.
     * @param comparingProfile      the next profile to compare against.
     * @return                      true if compatible, false if not.
     */
    private boolean isCleanlinessCompatible(Profile selectedProfile, Profile comparingProfile) {
        int tolerance = 1;

        Map<String, Object> selectedProfileCharacteristics = getCharacteristicsFromProfile(selectedProfile);
        int selectedCleanliness = (int) selectedProfileCharacteristics.get("cleanliness_level");
        Map<String, Object> comparingProfileCharacteristics = getCharacteristicsFromProfile(comparingProfile);
        int comparingCleanliness = (int) comparingProfileCharacteristics.get("cleanliness_level");

        return Math.abs(selectedCleanliness - comparingCleanliness) <= tolerance;
    }

    /**
     * Returns a list of profiles that are compatible by smoking habits.
     * Wrapper method for protected filtering function.
     *
     * @param selectedProfile   the current authenticated user's profile.
     * @param profiles          pool of profiles [users].
     * @return                  list of compatible users.
     */
    public List<Profile> filterBySmoking(Profile selectedProfile, List<Profile> profiles) {
        List<Profile> compatibleProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (isSmokingCompatible(selectedProfile, profile)) {
                compatibleProfiles.add(profile);
            }
        }
        return compatibleProfiles;
    }

    /**
     * Returns a list of profiles that are compatible by gender preference.
     * Wrapper method for protected filtering function.
     *
     * @param selectedProfile   current user profile.
     * @param profiles          pool of profiles [users].
     * @return                  list of compatible users.
     */
    public List<Profile> filterByGender(Profile selectedProfile, List<Profile> profiles) {
        List<Profile> compatibleProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (isGenderCompatible(selectedProfile, profile)) {
                compatibleProfiles.add(profile);
            }
        }
        return compatibleProfiles;
    }

    /**
     * Returns a list of profiles that are compatible by drinking habits.
     * Wrapper method for protected filtering function.
     *
     * @param selectedProfile   current user profile.
     * @param profiles          pool of profiles [users].
     * @return                  list of compatible users.
     */
    public List<Profile> filterByDrinking(Profile selectedProfile, List<Profile> profiles) {
        List<Profile> compatibleProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (isDrinkingCompatible(selectedProfile, profile)) {
                compatibleProfiles.add(profile);
            }
        }
        return compatibleProfiles;
    }

    /**
     * Returns a list of profiles that are compatible based on cleanliness levels.
     * Wrapper method for protected filtering function.
     *
     * @param selectedProfile   current user profile.
     * @param profiles          pool of profiles [users].
     * @return                  list of compatible users.
     */
    public List<Profile> filterByCleanliness(Profile selectedProfile, List<Profile> profiles) {
        List<Profile> compatibleProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (isCleanlinessCompatible(selectedProfile, profile)) {
                compatibleProfiles.add(profile);
            }
        }
        return compatibleProfiles;
    }

    /**
     * Returns a finalized list of profiles to be utilized for the modified K-Nearest-Neighbors algorithm.
     * Aggregate function for orchestrating the application of multiple filter functions in a sequence.
     * Gender -> Drinking -> Smoking -> Cleanliness
     *
     * @param selectedProfile   current user profile.
     * @param profiles          initial pool of profiles for filtering.
     * @return                  list of users aggregated from filtering.
     */
    public List<Profile> applyFilters(Profile selectedProfile, List<Profile> profiles) {
        // sequential filtering
        List<Profile> genderCompatibleProfiles = filterByGender(selectedProfile, profiles);
        List<Profile> drinkingCompatibleProfiles = filterByDrinking(selectedProfile, genderCompatibleProfiles);
        List<Profile> smokingCompatibleProfiles = filterBySmoking(selectedProfile, drinkingCompatibleProfiles);
        List<Profile> cleanlinessCompatibleProfiles = filterByCleanliness(selectedProfile, smokingCompatibleProfiles);

        List<Profile> finalCompatibleprofiles = cleanlinessCompatibleProfiles;

        return finalCompatibleprofiles;
    }

    /**
     * Returns the similarity-distance between the current profile and another.
     * It iterates through the preference set to obtain the key-value pair. It accommodates for differing
     * types of preferences (number, boolean, string) and handles them accordingly.
     * <p>
     * The String values are just ignored, since the only String sensitive variable relevant to us
     * has been filtered out.
     *
     * @param currentCharacteristics    the current authenticated user's characteristic set.
     * @param preferences               the current roommate preferences corresponding current user's profile.
     * @param compatibleProfiles        the profiles after filtering process was applied.
     * @return                          the [weighted] distance value between two users.
     */
    public double calculateWeightedDistance(Map<String, Object> currentCharacteristics, Map<String, Object> preferences,
                                                   List<Profile> compatibleProfiles) {
        double totalDistance = 0.0;

        for (Map.Entry<String, Object> entry : preferences.entrySet()) {
            String characteristicName = entry.getKey();
            Object characteristicValue = entry.getValue();

            double weight = 1.0;
            if (characteristicValue instanceof Number) {
                // check if the value is categorical or int
                weight = ((Number) characteristicValue).doubleValue();
            }
            else if (characteristicValue instanceof Boolean) {
                weight = (Boolean) characteristicValue ? 1.0 : 0.0;
            }
            else if (characteristicValue instanceof String) {
                continue;
            }

            Object currentCharacteristicValue = currentCharacteristics.get(characteristicName);

            for (Profile profile : compatibleProfiles) {
                Map<String, Object> profileCharacteristics = profile.getCharacteristics();
                Object profileCharacteristicValue = profileCharacteristics.get(characteristicName);

                double distance = calculateDistance(currentCharacteristicValue, profileCharacteristicValue);
                totalDistance += weight * distance;
            }
        }
        return totalDistance;
    }

    /**
     * Returns a number, basically a distance formula.
     * Helper method for calculateWeightedDistance.
     *
     * @param currentCharacteristicValue    value of characteristic of the current profile [user].
     * @param profileCharacteristicValue    value of characteristic of the current profile in the pool of compatible users.
     * @return                              the distance value between two users.
     */
    private double calculateDistance(Object currentCharacteristicValue, Object profileCharacteristicValue) {
        if (currentCharacteristicValue instanceof Number && profileCharacteristicValue instanceof Number) {
            return Math.abs(((Number) currentCharacteristicValue).doubleValue() - ((Number) profileCharacteristicValue).doubleValue());
        }
        else if (currentCharacteristicValue instanceof Boolean && profileCharacteristicValue instanceof Boolean) {
            return currentCharacteristicValue.equals(profileCharacteristicValue) ? 1.0 : 0.0;
        }
        return 1.0;
    }

    /**
     * Returns a list of profiles that are in order of nearest neighbor (similarity) to the current user.
     * This is a modified KNN algorithm that scales each (x2-x1)^2 with a weight w.
     * It will iterate through the compatible profiles and calculate the similarities between them, then
     * the pair profile and distance (called ProfileDistance) will be added to the heap.
     * <p> 
     * In order to select the profiles with the smallest distance (more similar to the current profile),
     * the function will utilize poll() and add that profile to the KNN list. After adding to the neighbors
     * list, the method will then automatically add the nearest neighbor (or match) to the {@link edu.depaul.cdm.se452.rfa.roomate.repository.RoommateMatchesRepository}.
     * <p>
     *
     * @param selectedProfile   current profile [user].
     * @param profiles          pool of profiles after filtering.
     * @param k                 number of profiles the modified KNN will return.
     * @return                  list of nearest profiles
     */
    public List<Profile> findKNearestNeighbors(Profile selectedProfile, List<Profile> profiles, int k) {
        // in the event k is too big for the number of profiles
        if (k > profiles.size()) {
            k = profiles.size();
        }

        // initialize a priority queue to keep track of nearest neighbors
        PriorityQueue<ProfileDistance> minHeap = new PriorityQueue<>(Comparator.comparingDouble(d -> d.distance));

        Map<String, Object> currentCharacteristics = selectedProfile.getCharacteristics();

        // calculate distance from current profile to every other profile [user]
        for (Profile target : profiles) {
            if (!target.equals(selectedProfile)) {
                double distance = calculateWeightedDistance(currentCharacteristics, getCharacteristicsFromProfile(target), profiles);
                minHeap.offer(new ProfileDistance(target, distance));
            }
        }

        int effectiveK = Math.min(k, minHeap.size());
        // initialize empty list to store the k-nearest-neighbors
        List<Profile> KNN = new ArrayList<>(effectiveK);
        User selectedUser = selectedProfile.getUser();

        // continue adding profiles to the KNN list until we have K-users or the minheap is empty
        while (KNN.size() < effectiveK && !minHeap.isEmpty()) {
            // retrieve and remove the profile with the smallest distance from minheap and add that to the KNN list
            ProfileDistance profileDistance = minHeap.poll();
            Profile matchedProfile = profileDistance.profile;
            double matchScore = profileDistance.distance == 0 ? 1.0 : 1 / profileDistance.distance;

            // save match using MatchStorageService
            saveMatchToRepo(selectedUser, matchedProfile.getUser(), matchScore);
            KNN.add(matchedProfile);
        }
        return KNN;
    }

    /**
     * Saves a match between two users with a given match score.
     *
     * @param user1         first user of match.
     * @param user2         second user of match.
     * @param matchScore    match score of both users.
     */
    public void saveMatchToRepo(User user1, User user2, double matchScore) {
        matchStorageService.addMatch(user1, user2, matchScore);
    }

    /**
     * Utility data class.
     */
    static class ProfileDistance {
        Profile profile;
        double distance;

        public ProfileDistance(Profile profile, double distance) {
            this.profile = profile;
            this.distance = distance;
        }
    }

    /**
     * Method for retrieving list of matches in Json format, used for front end display.
     * @param userId    Authenticated user's id.
     * @return          String of Json matches.
     */
    public String findMatchesForUser(int userId) {
        List<RoommateMatch> matches = roommateMatchesRepository.findByUserId(userId);
        List<Map<String, Object>> jsonMatchesList = new ArrayList<>();

//        Map<String, Object> jsonMatches = new HashMap<>();
        for (RoommateMatch match : matches) {
            int matchedUserId = match.getUserId2().getId();
            Profile user2Profile = profileService.getProfileByUserId(matchedUserId);

            // skip match if no profile
            if (user2Profile == null) {
                continue;
            }
            Map<String, Object> user2Characteristics = user2Profile.getCharacteristics();

            Map<String, Object> jsonMatch = new HashMap<>();

            Map<String, Object> profileJson = new HashMap<>();
            for (Map.Entry<String, Object> entry : user2Characteristics.entrySet()) {
                profileJson.put(entry.getKey(), entry.getValue());
            }

            jsonMatch.put("user_id", matchedUserId);
            jsonMatch.put("match_score", match.getMatchScore());
            jsonMatch.put("time_stamp", match.getMatchTs());
            jsonMatch.put("profile", profileJson);

            jsonMatchesList.add(jsonMatch);
        };

        Map<String, Object> response = new HashMap<>();
        response.put("matches", jsonMatchesList);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
            return jsonString;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}