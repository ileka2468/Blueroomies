package edu.depaul.cdm.se452.rfa.roomate.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
import edu.depaul.cdm.se452.rfa.roomate.repository.RoommateMatchesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MatchStorageServiceTest {

    @Mock
    private RoommateMatchesRepository roommateMatchesRepository;

    @InjectMocks
    private MatchStorageService matchStorageService;

    private User u1;
    private User u2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        User u1 = new User();
        u1.setId(1);
        User u2 = new User();
        u2.setId(2);
    }

    @Test
    void saveMatch() {
//        double matchScore = 85.7;
//        RoommateMatch mockMatch = new RoommateMatch();
//        mockMatch.setUserId1(u1);
//        mockMatch.setUserId2(u2);
//        mockMatch.setMatchScore(BigDecimal.valueOf(matchScore));
//
//        when(roommateMatchesRepository.save(any())).thenReturn(mockMatch);
//
//        // save the match
//        RoommateMatch savedMatch = matchStorageService.saveMatch(u1, u2, matchScore);
//
//        // assertions
//        assertEquals(1, savedMatch.getUserId1().getId());
//        assertEquals(2, savedMatch.getUserId2().getId());
//        assertEquals(BigDecimal.valueOf(matchScore), savedMatch.getMatchScore());
//        assertEquals(LocalDate.now(), savedMatch.getMatchTs());
//        verify(roommateMatchesRepository, times(1)).save(any(RoommateMatch.class));
    }
}