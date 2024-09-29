package edu.depaul.cdm.se452.rfa.profileManagement.service;
import edu.depaul.cdm.se452.rfa.profileManagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.profileManagement.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

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
}
