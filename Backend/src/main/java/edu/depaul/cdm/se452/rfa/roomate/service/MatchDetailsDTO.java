package edu.depaul.cdm.se452.rfa.roomate.service;

import edu.depaul.cdm.se452.rfa.profileManagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.roomate.repository.RoommateMatchesRepository;
import edu.depaul.cdm.se452.rfa.roomate.service.ProfilesDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * DTO for returning match details for the current user.
 * Example:
 * {
 *   "matches": [
 *     {
 *       "user_id": 5,
 *       "match_score": 87,
 *       "profile": {
 *         "name": "John Doe",
 *         "bio": "Looking for a quiet roommate.",
 *         "budget": 900,
 *         "oncampus": false,
 *         "cleanliness": "high",
 *         "smoking": "non-smoker",
 *         "pets": "no",
 *         "noise": "quiet",
 *         "proximity": "campus"
 *       }
 *     },
 *     {
 *       "user_id": 8,
 *       "match_score": 82,
 *       "profile": {
 *         "name": "Jane Smith",
 *         "bio": "Night owl, loves pets.",
 *         "budget": 700,
 *         "oncampus": false,
 *         "cleanliness": "medium",
 *         "smoking": "non-smoker",
 *         "pets": "yes",
 *         "noise": "moderate",
 *         "proximity": "off-campus"
 *       }
 *     }
 *   ]
 * }
 */
public class MatchDetailsDTO {
    private RoommateMatchesRepository roommateMatchesRepository;
    private int userID;
    private BigDecimal matchScore;
    private ProfilesDTO profile;

    public MatchDetailsDTO(int userID, BigDecimal matchScore, ProfilesDTO profile) {
        this.userID = userID;
        this.matchScore = matchScore;
        this.profile = profile;
    }
}