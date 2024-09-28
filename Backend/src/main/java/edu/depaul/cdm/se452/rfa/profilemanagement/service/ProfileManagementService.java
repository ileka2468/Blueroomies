package edu.depaul.cdm.se452.rfa.profilemanagement.service;

import edu.depaul.cdm.se452.rfa.profilemanagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.profilemanagement.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProfileManagementService {
    private final ProfileRepository profileRepository;

    public ProfileManagementService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfile(User user) {
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setBio(null);
        profile.setIsActivelyLooking(false);
        profile.setCharacteristics(generateDefaultCharacteristics());
        String defaultPFP = String.format("https://ui-avatars.com/api/?name=%s+%s/?background=random", user.getFirstName(), user.getLastName());
        profile.setPfpImage(defaultPFP);
        profileRepository.save(profile);
        return profile;
    }

    public Map<String, Object> generateDefaultCharacteristics () {
        return new Characteristics().toMap();
    }
}
