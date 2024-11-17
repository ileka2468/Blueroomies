package edu.depaul.cdm.se452.rfa.preferences.controller;

import com.fasterxml.jackson.databind.JsonNode;

import edu.depaul.cdm.se452.rfa.preferences.entity.Preference;
import edu.depaul.cdm.se452.rfa.preferences.service.PreferencesService;
import edu.depaul.cdm.se452.rfa.profilemanagement.service.ProfileService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    private PreferencesService preferenceService;

    @Autowired
    public PreferenceController(PreferencesService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> savePreferences(@PathVariable Integer userId, @RequestBody Map<String, Object> prefCharacteristics) {
        try {
            preferenceService.saveOrUpdatePreferences(userId, prefCharacteristics);
            return ResponseEntity.ok("Preferences saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving preferences. Please try again.");
        }
    }
}
