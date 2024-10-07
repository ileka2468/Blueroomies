package edu.depaul.cdm.se452.rfa.profileManagement.service;

import edu.depaul.cdm.se452.rfa.authentication.repository.UserRepository;
import edu.depaul.cdm.se452.rfa.profileManagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.profileManagement.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ProfileManagementService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileManagementService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

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

    public Profile loadProfileByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return null;
        }
        Optional<Profile> profile = profileRepository.findProfileByUser(user.get());
        return profile.orElse(null);
    }

    public Map<String, Object> generateDefaultCharacteristics () {
        return new edu.depaul.cdm.se452.rfa.profilemanagement.service.Characteristics().toMap();
    }
}
