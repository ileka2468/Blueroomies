package edu.depaul.cdm.se452.rfa.profileManagement.service;

import edu.depaul.cdm.se452.rfa.authentication.repository.UserRepository;
import edu.depaul.cdm.se452.rfa.profileManagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.profileManagement.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.Map;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;


    public Profile createProfile(User user) {
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setBio(null);
        profile.setIsActivelyLooking(false);
        profile.setCharacteristics(generateDefaultCharacteristics());
        String defaultPFP = String.format("https://ui-avatars.com/api/?name=%s+%s&background=random", user.getFirstName(), user.getLastName());
        profile.setPfpImage(defaultPFP);
        profileRepository.save(profile);
        return profile;
    }
    // Create or update a profile
//    public Profile saveProfile(Profile profile) {
//        return profileRepository.save(profile);
//    }
    public Profile saveProfile(Profile profile) {
        // If profile has an ID, it's an update request; otherwise, it's a new profile creation
        if (profile.getId() != null) {
            // Fetch the existing profile to update it
            Profile existingProfile = profileRepository.findById(profile.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

            // If user is provided in the request body, update it; otherwise, keep the existing user
            if (profile.getUser() != null) {
                existingProfile.setUser(profile.getUser());  // If a new user is provided, set it
            } else {
                // If no user is provided in the update, retain the existing user
                existingProfile.setUser(existingProfile.getUser());
            }

            // Update other fields only if they're not null
            if (profile.getBio() != null) {
                existingProfile.setBio(profile.getBio());
            }
            if (profile.getCharacteristics() != null) {
                existingProfile.setCharacteristics(profile.getCharacteristics());
            }
            if (profile.getPfpImage() != null) {
                existingProfile.setPfpImage(profile.getPfpImage());
            }

            // Save the updated profile
            return profileRepository.save(existingProfile);
        } else {
            // If no ID is provided, create a new profile
            return profileRepository.save(profile);
        }
    }


    // Get a profile by its ID
    public Optional<Profile> getProfileById(Integer profileId) {
        return profileRepository.findById(profileId);
    }

    // Get a profile by user ID
    public Profile getProfileByUserId(Integer userId) {
        return profileRepository.findByUserId(userId);
    }

    // Delete a profile by ID
    public void deleteProfile(Integer profileId) {
        profileRepository.deleteById(profileId);
    }
    public Profile loadProfileByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return null;
        }
        Optional<Profile> profile = profileRepository.findProfileByUser(user.get());
        return profile.orElse(null);
    }

    public Map<String, Object> generateDefaultCharacteristics () {
        return new Characteristics().toMap();
    }
}
