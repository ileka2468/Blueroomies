package edu.depaul.cdm.se452.rfa.preferences.controller;

import com.fasterxml.jackson.databind.JsonNode;

import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.authentication.security.JwtTokenProvider;
import edu.depaul.cdm.se452.rfa.authentication.service.CustomUserDetailsService;
import edu.depaul.cdm.se452.rfa.preferences.entity.Preference;
import edu.depaul.cdm.se452.rfa.preferences.service.PreferencesService;
import edu.depaul.cdm.se452.rfa.profileManagement.service.ProfileService;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private PreferencesService preferenceService;

    @Autowired
    public PreferenceController(PreferencesService preferenceService, JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.preferenceService = preferenceService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PutMapping("/save")
    public ResponseEntity<String> savePreferences(@RequestBody Map<String, Object> prefCharacteristics, HttpServletRequest request) {
        try {
            String jwt = jwtTokenProvider.getJwtFromRequest(request);
            String username = jwtTokenProvider.getUsernameFromJWT(jwt);
            User user = customUserDetailsService.getUserByUsername(username);
            preferenceService.saveOrUpdatePreferences(user.getId(), prefCharacteristics);
            return ResponseEntity.ok("Preferences saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving preferences. Please try again.");
        }
    }
}
