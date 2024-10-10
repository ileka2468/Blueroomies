package edu.depaul.cdm.se452.rfa.roomate.controller;

import edu.depaul.cdm.se452.rfa.roomate.repository.RoommateMatchesRepository;
import edu.depaul.cdm.se452.rfa.roomate.service.RoommateMatcherService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@RestController
@CrossOrigin
@RequestMapping("/api/matches")
public class MatchController {
    RoommateMatchesRepository roommateMatchesRepository;
    // RoommateMatcherService roommateMatcherService;

    public MatchController(RoommateMatchesRepository matchesRepository) {
        this.roommateMatchesRepository = matchesRepository;
    }

    @PostMapping("/run")
    public ResponseEntity<?> runMatchingAlgorithm(){
        return ResponseEntity.ok("Protected route data! Yum!!!!");

        /**
         * // matcher service needs all the profiles (fetched from repository or postgresql, maybe)
         * // use static method from RoommateMatcherService to apply filters
         *
         * int userId = getUserID();
         *
         * List<Profile> allProfiles = repository.findAll()
         * Profile currentProfile = getCurrentSession.profile() // placeholder for ProfileService's getProfileByUserId
         *
         * // apply filters and get corresponding profiles
         * List<Profile> filteredProfiles = roommateMatcherService.applyFilters(currentProfile, allProfiles);
         * int numOfMatches = 5;
         * List<Profile> topMatches = roommateMatcherService.findKNearestNeighbors(currentProfile, filteredProfiles, numOfMatches, weightsMapping);
         *
         * retyrn ResponseEntity.ok(topMatches);
         *
         *
         */
    }

    /**
     * private int getCurrentUserID() {
     *     // get a username through a JWT Token
     *     return 0;
     * }
     */
}
