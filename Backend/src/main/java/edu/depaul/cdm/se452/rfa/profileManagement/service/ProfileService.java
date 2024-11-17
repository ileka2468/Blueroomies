package edu.depaul.cdm.se452.rfa.profilemanagement.service;

import edu.depaul.cdm.se452.rfa.authentication.repository.UserRepository;
import edu.depaul.cdm.se452.rfa.profilemanagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.profilemanagement.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.depaul.cdm.se452.rfa.preferences.*;
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
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
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
        return new Preferences().toMap();
    }
}
