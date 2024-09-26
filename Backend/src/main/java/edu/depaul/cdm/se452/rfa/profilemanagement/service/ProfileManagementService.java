package edu.depaul.cdm.se452.rfa.profilemanagement.service;

import edu.depaul.cdm.se452.rfa.profilemanagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.profilemanagement.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProfileManagementService {
    private ProfileRepository profileRepository;


    public ProfileManagementService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public boolean createProfile(User user) {
        Integer userId = user.getId();
        Profile profile = new Profile();
        profile.setId(userId);
        profile.setBio(null);
        profile.setIsActivelyLooking(false);
        profile.setCharacteristics(generateDefaultCharacteristics());
        profileRepository.save(profile);
        return true;
    }

    public Map<String, Object> generateDefaultCharacteristics () {
        return new Characteristics().toMap();
    }

}
