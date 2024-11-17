package edu.depaul.cdm.se452.rfa.preferences.service;
 
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.authentication.repository.UserRepository;
import edu.depaul.cdm.se452.rfa.preferences.entity.Preference;
import edu.depaul.cdm.se452.rfa.preferences.repository.PreferenceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PreferencesService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    /**
     * Saves or updates preferences for a user.
     *
     * @param userId the ID of the user
     * @param prefCharacteristics the JSON object containing preferences
     * @return the saved or updated Preference entity
     */
    public Preference saveOrUpdatePreferences(Integer userId, Map<String, Object> prefCharacteristics) {
        // Check if preferences for the user already exist
        Optional<Preference> existingPreference = preferenceRepository.findByUserId(userId);

        if (existingPreference.isPresent()) {
            // Update existing preferences
            Preference preference = existingPreference.get();
            preference.setPrefCharacteristics(prefCharacteristics); // Lombok-generated setter
            return preferenceRepository.save(preference);
        } else {
            // Create new preferences for the user
            Preference newPreference = new Preference();
            newPreference.setUserId(userId); // Lombok-generated setter
            newPreference.setPrefCharacteristics(prefCharacteristics); // Lombok-generated setter
            return preferenceRepository.save(newPreference);
        }
    }
}
