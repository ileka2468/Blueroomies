package edu.depaul.cdm.se452.rfa.roomate.service;


import edu.depaul.cdm.se452.rfa.profilemanagement.entity.Profile;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * The RoommateMatcher class provides methods to calculate the weighted distance
 * between users based on their preferences and to find the k-nearest neighbors
 * using a modified K-Nearest Neighbors (KNN) algorithm.
 */

@Service
public class RoommateMatcherService {

    private double[] weights;

    private static Map<String, Object> getCharacteristicsFromProfile(Profile selectedProfile){
        return selectedProfile.getCharacteristics();
    }

    /**
     *
     * pre-filtering step before applying KNN
     *
     * each filter takes in a list of profiles parameter derived from the previous filter
     *
     */
    private static boolean isGenderCompatible(Map<String, Object> currentCharacteristics, List<Profile> profiles) {
        Object gender = currentCharacteristics.get("gender_preference");
        for (Profile profile : profiles) {
            if (profile.getCharacteristics().get("gender_preference").equals(gender)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSmokingCompatible(Map<String, Object> currentCharacteristics, List<Profile> profiles) {
        Object smoking = currentCharacteristics.get("smoking_preference");
        for (Profile profile : profiles) {
            if (profile.getCharacteristics().get("smoking_preference").equals(smoking)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDrinkingCompatible(Map<String, Object> currentCharacteristics, List<Profile> profiles) {
        Object drinking = currentCharacteristics.get("alcohol_usage");
        for (Profile profile : profiles) {
            if (profile.getCharacteristics().get("alcohol_usage").equals(drinking)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCleanlinessCompatible(Map<String, Object> currentCharacteristics, List<Profile> profiles) {
        Object cleanliness = currentCharacteristics.get("cleanliness_level");
        for (Profile profile : profiles) {
            if (profile.getCharacteristics().get("cleanliness_level").equals(cleanliness)) {
                return true;
            }
        }
        return false;
    }

    public static List<Profile> filterBySmoking(List<Profile> profiles) {
        List<Profile> compatibleProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (isSmokingCompatible(profile.getCharacteristics(), profiles)) {
                compatibleProfiles.add(profile);
            }
        }
        return compatibleProfiles;
    }


    public static List<Profile> filterByGender(List<Profile> profiles) {
        List<Profile> compatibleProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (isGenderCompatible(profile.getCharacteristics(), profiles)) {
                compatibleProfiles.add(profile);
            }
        }
        return compatibleProfiles;
    }

    public static List<Profile> filterByDrinking(List<Profile> profiles) {
        List<Profile> compatibleProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (isDrinkingCompatible(profile.getCharacteristics(), profiles)) {
                compatibleProfiles.add(profile);
            }
        }
        return compatibleProfiles;
    }

    public static List<Profile> filterByCleanliness(List<Profile> profiles) {
        List<Profile> compatibleProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            if (isCleanlinessCompatible(profile.getCharacteristics(), profiles)) {
                compatibleProfiles.add(profile);
            }
        }
        return compatibleProfiles;
    }

    public static List<Profile> applyFilters(Profile selectedProfile, List<Profile> profiles) {
        // sequential filtering
        List<Profile> genderCompatibleProfiles = filterByGender(profiles);
        List<Profile> drinkingCompatibleProfiles = filterByDrinking(genderCompatibleProfiles);
        List<Profile> smokingCompatibleProfiles = filterBySmoking(drinkingCompatibleProfiles);
        List<Profile> cleanlinessCompatibleProfiles = filterByCleanliness(smokingCompatibleProfiles);

        List<Profile> finalCompatibleprofiles = cleanlinessCompatibleProfiles;

        return finalCompatibleprofiles;
    }

    /**
     *
     * @param profile: get weights from preferences json
     */
    private void getWeights(Profile profile) {
        // utilize json parser to get double[] weights and assign to weights;
    }

    /**
     *
     * @param preferences: raw preferences value before it is normalized for cases such as booleans and strings.
     */
    private static void normalizePreferences(Map<String, Object> preferences) {
        // TODO
    }

    /**
     *
     * @param currentCharacteristics: the current authenticated user's characteristic set.
     * @param preferences: the current roommate preferences corresponding current user's profile.
     * @param compatibleProfiles: the profiles after filtering process was applied.
     * @return: the [weighted] distance value between two users.
     */
    public static double calculateWeightedDistance(Map<String, Object> currentCharacteristics, Map<String, Object> preferences,
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
     *
     * helper method for calculateWeightedDistance
     * @param currentCharacteristicValue: value of characteristic of the current profile [user].
     * @param profileCharacteristicValue: value of characteristic of the current profile in the pool of compatible users.
     * @return: the distance value between two users.
     */
    private static double calculateDistance(Object currentCharacteristicValue, Object profileCharacteristicValue) {
        if (currentCharacteristicValue instanceof Number && profileCharacteristicValue instanceof Number) {
            return Math.abs(((Number) currentCharacteristicValue).doubleValue() - ((Number) profileCharacteristicValue).doubleValue());
        }
        else if (currentCharacteristicValue instanceof Boolean && profileCharacteristicValue instanceof Boolean) {
            return currentCharacteristicValue.equals(profileCharacteristicValue) ? 1.0 : 0.0;
        }
        return 1.0;
    }

    /**
     *
     * @param selectedProfile: current profile [user].
     * @param profiles: pool of profiles after filtering.
     * @param k: number of profiles the modified KNN will return.
     * @return
     * TODO: consider the event that K is too large for the profiles pool.
     */
    public static List<Profile> findKNearestNeighbors(Profile selectedProfile, List<Profile> profiles, int k) {
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

        // initialize empty list to store the k-nearest-neighbors
        List<Profile> KNN = new ArrayList<>();
        // continue adding profiles to the KNN list until we have K-users or the minheap is empty
        while (KNN.size() < k && !minHeap.isEmpty()) {
            // retrieve and remove the profile with the smallest distance from minheap and add that to the KNN list
            KNN.add(minHeap.poll().profile);
        }
        return KNN;
    }

    static class ProfileDistance {
        Profile profile;
        double distance;

        public ProfileDistance(Profile profile, double distance) {
            this.profile = profile;
            this.distance = distance;
        }
    }
}