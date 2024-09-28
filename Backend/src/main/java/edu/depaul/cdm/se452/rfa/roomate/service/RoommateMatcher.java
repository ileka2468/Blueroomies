package edu.depaul.cdm.se452.rfa.roomate.service;

import edu.depaul.cdm.se452.rfa.authentication.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RoommateMatcher {
    public static double calculateWeightedDistance(User u1, User u2, double[] weights) {
        // TODO: implement a getPreferences() that returns a Map<String, Double>
        // and that jsonPreferences is a Map<String, Double> where key is the preference name
        // and value is the preference weight.

        Map<String, Double> preferences1 = u1.getPreferences();
        Map<String, Double> preferences2 = u2.getPreferences();

        double sumWeightedDistances = 0.0;
        double sumWeights = 0.0;

        // iterate over all preferences, assuming both users have same preference keys
        for (String key : preferences1.keySet()) {
            double pref1 = preferences1.get(key);
            double pref2 = preferences2.get(key);

            // scale the distance with corresponding weight
            double weight = weights.length > 0 ? weights[0] : 1.0; // Default weight if not provided
            sumWeightedDistances += weight * Math.abs(pref1 - pref2);
            sumWeights += weight;
        }

        // Calculate weighted distance
        return sumWeights > 0 ? sumWeightedDistances / sumWeights : 0.0;
    }

    // KNN algorithm to find nearest neighbors
    public static List<User> findKNearestNeighbors(User selectedUser, List<User> users, int k, double[] weights) {
        // initialize a list for distances per user INEFFICIENT
        List<UserDistance> distances = new ArrayList<>();

        // calculate distance from target user to every other user
        for (User user: users) {
            if (user.id != selectedUser) {
                double distance = calculateWeightedDistance(selectedUser, user, weights);
                // TODO: store the selected-user distance to another user somewhere, using an inner class for now
                distances.add(new UserDistance(user, distance));
            }
        }

        // sort the distance in ascending order
        Collections.sort(distances, Comparator.comparingDouble(d -> d.distance));

        // get the top k nearest neighbors
        List<User> KNN = new ArrayList<>();
        for (int i=0; i < Math.min(k, distances.size()); i++) {
            KNN.add(distances.get(i).user);
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