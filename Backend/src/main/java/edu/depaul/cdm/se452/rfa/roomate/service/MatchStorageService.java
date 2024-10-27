package edu.depaul.cdm.se452.rfa.roomate.service;

import edu.depaul.cdm.se452.rfa.authentication.entity.User;

import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
import edu.depaul.cdm.se452.rfa.roomate.repository.RoommateMatchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The MatchStorageService handles the storage of a match after an agreement between the two users.
 * Confirming the agreement directs the request here.
 */
@Service
public class MatchStorageService {
    private final RoommateMatchesRepository matchesRepo;

    /**
     * Constructor for service.
     * @param matchesRepo   {@link RoommateMatch}.
     */
    @Autowired
    public MatchStorageService(RoommateMatchesRepository matchesRepo) {
        this.matchesRepo = matchesRepo;
    }

    /**
     * Saves a new match record between two users with the match score. This method creates a new
     * {@link RoommateMatch} entity, sets the users, match score, and timestamp, then saves to the repository.
     *
     * @param user1             the first user in the match.
     * @param user2             the second user in the match.
     * @param matchScore        the similarity score between the two users (double).
     * @return                  the saved {@link RoommateMatch} entity.
     */
    public RoommateMatch saveMatch(User user1, User user2, double matchScore) {
        RoommateMatch match = new RoommateMatch();
        match.setUserId1(user1);
        match.setUserId2(user2);
        BigDecimal score = BigDecimal.valueOf(matchScore);
        match.setMatchScore(score);
        match.setMatchTs(LocalDate.now());
        return matchesRepo.save(match);
    }

    // TODO: implement in the controller


}
