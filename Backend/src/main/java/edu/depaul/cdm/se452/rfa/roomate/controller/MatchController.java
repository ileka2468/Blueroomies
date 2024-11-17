package edu.depaul.cdm.se452.rfa.roomate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.authentication.security.JwtTokenProvider;
import edu.depaul.cdm.se452.rfa.authentication.service.CustomUserDetailsService;
import edu.depaul.cdm.se452.rfa.profileManagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.profileManagement.service.ProfileService;
import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
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
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@Getter
@Setter
@RestController
@CrossOrigin
@RequestMapping("/api/matches")
public class MatchController {
    private final CustomUserDetailsService customUserDetailsService;
    RoommateMatchesRepository roommateMatchesRepository;
    ProfileRepository profileRepository;
    JwtTokenProvider jwtTokenProvider;
    RoommateMatcherService roommateMatcherService;
    UserRepository userRepository;
    ProfileService profileService;

    public MatchController(RoommateMatchesRepository matchesRepository, ProfileRepository profileRepository, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, RoommateMatcherService roommateMatcherService, CustomUserDetailsService customUserDetailsService, ProfileService profileService) {
        this.roommateMatchesRepository = matchesRepository;
        this.profileRepository = profileRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.roommateMatcherService = roommateMatcherService;
        this.customUserDetailsService = customUserDetailsService;
        this.profileService = profileService;
    }

    @PostMapping("/run")
    public ResponseEntity<?> runMatchingAlgorithm(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsernameFromJWT(jwtTokenProvider.getJwtFromRequest(request));
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        List<Profile> allProfiles = profileRepository.findAll();
        Profile currentProfile = profileRepository.findProfileByUser(user).orElse(null);

        if (currentProfile == null) {
            return ResponseEntity.status(401).build();
        }

        // apply filters and get corresponding profiles
        List<Profile> filteredProfiles = roommateMatcherService.applyFilters(currentProfile, allProfiles);
        log.info(filteredProfiles.toString());
        int numOfMatches = 5;
        List<Profile> topMatches = roommateMatcherService.findKNearestNeighbors(currentProfile, filteredProfiles, numOfMatches);
        log.info(topMatches.toString());

        String response = convertCurrentRunToJson(topMatches, user);
        return ResponseEntity.ok(response);
    }

    private String convertCurrentRunToJson(List<Profile> topMatches, User currentUser) {
        List<Map<String, Object>> jsonMatchesList = new ArrayList<>();

        for (Profile matchedProfile : topMatches) {
            User matchedUser = matchedProfile.getUser();

            // Skip if we're looking at the current user's profile
            if (matchedUser.getId().equals(currentUser.getId())) {
                continue;
            }

            // Get the most recent match record for these users
            List<RoommateMatch> matches = roommateMatchesRepository.findMatchesByUser(currentUser);
            Optional<RoommateMatch> latestMatch = matches.stream()
                    .filter(m -> (m.getUserId1().equals(currentUser) && m.getUserId2().equals(matchedUser)) ||
                            (m.getUserId1().equals(matchedUser) && m.getUserId2().equals(currentUser)))
                    .max(Comparator.comparing(RoommateMatch::getMatchTs));

            if (latestMatch.isPresent()) {
                Map<String, Object> jsonMatch = new HashMap<>();
                RoommateMatch match = latestMatch.get();

                jsonMatch.put("user_id", matchedUser.getId());
                jsonMatch.put("match_score", match.getMatchScore());
                jsonMatch.put("time_stamp", match.getMatchTs());

                // Add profile characteristics
                Map<String, Object> profileJson = new HashMap<>();
                Map<String, Object> characteristics = matchedProfile.getCharacteristics();
                for (Map.Entry<String, Object> entry : characteristics.entrySet()) {
                    profileJson.put(entry.getKey(), entry.getValue().toString());
                }
                jsonMatch.put("profile", profileJson);

                jsonMatchesList.add(jsonMatch);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("matches", jsonMatchesList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMatchesFromUser(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsernameFromJWT(jwtTokenProvider.getJwtFromRequest(request));
        User user = customUserDetailsService.getUserByUsername(username);
        List<RoommateMatch> userMatches = roommateMatchesRepository.findMatchesByUser(user);


        Map<Object, Object> response = generateMatchResponse(userMatches, profileService, user);
        return ResponseEntity.ok(response);
    }

    public static Map<Object, Object> generateMatchResponse(List<RoommateMatch> matches, ProfileService profileService, User currentUser) {
        List<Map<String, Object>> matchList = new ArrayList<>();

        for (RoommateMatch match : matches) {
            Map<String, Object> matchObject = new HashMap<>();
            matchObject.put("matchid", match.getId());
            matchObject.put("matchscore", match.getMatchScore());

            // Determine the matched user
            User matchedUser = match.getUserId1().equals(currentUser) ? match.getUserId2() : match.getUserId1();

            matchObject.put("id", matchedUser.getId());
            matchObject.put("username", matchedUser.getUsername());
            matchObject.put("firstname", matchedUser.getFirstName());
            matchObject.put("lastname", matchedUser.getLastName());


            String pfpUrl = profileService.loadProfileByUsername(matchedUser.getUsername()).getPfpImage();
            matchObject.put("pfp", pfpUrl);

            matchList.add(matchObject);
        }

        Map<Object, Object> responseMap = new HashMap<>();
        responseMap.put("matches", matchList);

        return responseMap;
    }

}
