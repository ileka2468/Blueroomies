package edu.depaul.cdm.se452.rfa.roomate.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
import edu.depaul.cdm.se452.rfa.roomate.repository.RoommateMatchesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MatchStorageServiceTest {
    @Mock
    private RoommateMatchesRepository matchesRepo;

    @InjectMocks
    private MatchStorageService matchStorageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMatch() {
        User u1 = new User();
        u1.setId(1);
        User u2 = new User();
        u2.setId(2);

        RoommateMatch expectedMatch = new RoommateMatch();
        expectedMatch.setUserId1(u1);
        expectedMatch.setUserId2(u2);
        expectedMatch.setMatchScore(BigDecimal.valueOf(0.07));
        expectedMatch.setMatchTs(LocalDate.now());

        // mocking repository response
        when(matchesRepo.save(any(RoommateMatch.class))).thenReturn(expectedMatch);

        RoommateMatch savedMatch = matchStorageService.addMatch(u1, u2, 0.07);
        assertEquals(expectedMatch.getMatchScore(), savedMatch.getMatchScore());
        assertEquals(expectedMatch.getMatchTs(), savedMatch.getMatchTs());
        assertEquals(expectedMatch.getUserId1().getId(), savedMatch.getUserId1().getId());
        assertEquals(expectedMatch.getUserId2().getId(), savedMatch.getUserId2().getId());
    }

    @Test
    void getMatchById() {
        int id = 1;
        User u1 = new User();
        u1.setId(1);
        User u2 = new User();
        u2.setId(2);

        RoommateMatch savedMatch = new RoommateMatch();
        savedMatch.setUserId1(u1);
        savedMatch.setUserId2(u2);
        savedMatch.setMatchScore(BigDecimal.valueOf(0.07));
        savedMatch.setMatchTs(LocalDate.now());
        savedMatch.setId(id);

        // mocking repository response
        when(matchesRepo.findRoommateMatchById(id)).thenReturn(savedMatch);

        RoommateMatch expectedMatch = matchStorageService.findMatchById(id);

        assertNotNull(expectedMatch, "Match should not be null");
        assertEquals(id, expectedMatch.getId(), "Match ID should be the same as expected");
        assertEquals(savedMatch, expectedMatch, "Returned match should match the expected match");

        // check that findById was called once with the specified ID
        verify(matchesRepo, times(1)).findRoommateMatchById(id);
    }

    @Test
    void deleteMatch() {
        int id = 1;
        User u1 = new User();
        u1.setId(1);
        User u2 = new User();
        u2.setId(2);

        RoommateMatch savedMatch = new RoommateMatch();
        savedMatch.setUserId1(u1);
        savedMatch.setUserId2(u2);
        savedMatch.setMatchScore(BigDecimal.valueOf(0.07));
        savedMatch.setMatchTs(LocalDate.now());
        savedMatch.setId(id);

        when(matchesRepo.findRoommateMatchById(id)).thenReturn(savedMatch);

        matchStorageService.removeMatchById(id);

        verify(matchesRepo, times(1)).deleteById(id);

        when(matchesRepo.findById(id)).thenReturn(Optional.empty());
        Optional<RoommateMatch> deletedMatch = matchesRepo.findById(id);
        assertTrue(deletedMatch.isEmpty(), "The match should be deleted and not found in the repository.");
    }
}