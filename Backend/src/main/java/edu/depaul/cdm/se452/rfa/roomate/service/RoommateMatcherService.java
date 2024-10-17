package edu.depaul.cdm.se452.rfa.roomate.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.profilemanagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.profilemanagement.service.ProfileManagementService;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * The RoommateMatcher class provides methods to calculate the weighted distance
 * between users based on their preferences and to find the k-nearest neighbors
 * using a modified K-Nearest Neighbors (KNN) algorithm.
 */

@Service
public class RoommateMatcherService {
    private Map<String, Object> getCharacteristicsFromProfile(Profile selectedProfile){
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

    public List<Profile> applyFilters(Profile selectedProfile, List<Profile> profiles) {
        // sequential filtering
        List<Profile> genderCompatibleProfiles = filterByGender(profiles);
        List<Profile> drinkingCompatibleProfiles = filterByDrinking(genderCompatibleProfiles);
        List<Profile> smokingCompatibleProfiles = filterBySmoking(drinkingCompatibleProfiles);
        List<Profile> cleanlinessCompatibleProfiles = filterByCleanliness(smokingCompatibleProfiles);

        List<Profile> finalCompatibleprofiles = cleanlinessCompatibleProfiles;

        return finalCompatibleprofiles;
    }

    public static double calculateWeightedDistance(User u1, User u2, double[] weights) {
        // TODO: implement a getPreferences() that returns a Map<String, Double>
        // and that jsonPreferences is a Map<String, Double> where key is the preference name
        // and value is the preference weight.

        Map<String, Double> preferences1 = u1.getPreferences();
        Map<String, Double> preferences2 = u2.getPreferences();

        double sumWeightedDistances = 0.0;
        double sumWeights = 0.0;

        // iterate over all preferences since both users have the same preference keys
        for (String key : preferences1.keySet()) {
            double pref1 = preferences1.get(key);
            double pref2 = preferences2.get(key);

            if (pref1 == null || pref2 == null) {
                // skip if a user is missing a preference
                continue;
            }

            // scale the distance with corresponding weight
            double weight = weights.length > 0 ? weights[0] : 1.0; // default weight of 1 if not provided
            sumWeightedDistances += weight * Math.abs(pref1 - pref2);
            sumWeights += weight;
        }

        // calculate weighted distance
        return sumWeights > 0 ? sumWeightedDistances / sumWeights : 0.0;
    }

    // KNN algorithm to find nearest neighbors
    public static List<User> findKNearestNeighbors(User selectedUser, List<User> users, int k, double[] weights) {
        // initialize a priority queue to keep track of nearest neighbors
        PriorityQueue<UserDistance> minHeap = new PriorityQueue<>(Comparator.comparingDouble(d -> d.distance));

        // calculate distance from target user to every other user
        for (User user: users) {
            if (!user.getId().equals(selectedUser.getId())) {
                double distance = calculateWeightedDistance(selectedUser, user, weights);
                // wrap the user and distance in UserDistance and add to priority queue
                minHeap.offer(new UserDistance(user, distance));
            }
        }

        // initialize empty list to store the k-nearest-neighbors
        List<User> KNN = new ArrayList<>();
        // continue adding users to the KNN list until we have K-users or the minheap is empty
        while (KNN.size() < k && !minHeap.isEmpty()) {
            // retrieve and remove the user with the smallest distance from minheap and add that user to the KNN list
            KNN.add(minHeap.poll().user);
        }

        return KNN;
    }

    static class UserDistance {
        User user;
        double distance;

        public UserDistance(User user, double distance) {
            this.user = user;
            this.distance = distance;
        }
    }
}