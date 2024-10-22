package edu.depaul.cdm.se452.rfa.roomate.controller;

import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.authentication.security.JwtTokenProvider;
import edu.depaul.cdm.se452.rfa.profileManagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.roomate.repository.RoommateMatchesRepository;
import edu.depaul.cdm.se452.rfa.roomate.service.RoommateMatcherService;
import edu.depaul.cdm.se452.rfa.authentication.repository.UserRepository;
import edu.depaul.cdm.se452.rfa.profileManagement.repository.ProfileRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Getter
@Setter
@RestController
@CrossOrigin
@RequestMapping("/api/matches")
public class MatchController {
    RoommateMatchesRepository roommateMatchesRepository;
    ProfileRepository profileRepository;
    JwtTokenProvider jwtTokenProvider;
    RoommateMatcherService roommateMatcherService;
    UserRepository userRepository;

    public MatchController(RoommateMatchesRepository matchesRepository, ProfileRepository profileRepository, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, RoommateMatcherService roommateMatcherService) {
        this.roommateMatchesRepository = matchesRepository;
        this.profileRepository = profileRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.roommateMatcherService = roommateMatcherService;
    }

    @PostMapping("/run")
    public ResponseEntity<?> runMatchingAlgorithm(HttpServletRequest request) {

        // matcher service needs all the profiles (fetched from repository or postgresql, maybe)
        // use static method from RoommateMatcherService to apply filters
        String username = jwtTokenProvider.getUsernameFromJWT(jwtTokenProvider.getJwtFromRequest(request));
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

         List<Profile> allProfiles = profileRepository.findAll();
//         log.info(allProfiles.toString());
         Profile currentProfile = profileRepository.findProfileByUser(user).orElse(null);

         if (currentProfile == null) {
             return ResponseEntity.status(401).build();
         }
//         log.info(String.valueOf(currentProfile));

         // apply filters and get corresponding profiles
         List<Profile> filteredProfiles = roommateMatcherService.applyFilters(currentProfile, allProfiles);
         log.info(filteredProfiles.toString());
         int numOfMatches = 5;
         List<Profile> topMatches = roommateMatcherService.findKNearestNeighbors(currentProfile, filteredProfiles, numOfMatches);
         log.info(topMatches.toString());
         return ResponseEntity.ok(topMatches);
    }
}
