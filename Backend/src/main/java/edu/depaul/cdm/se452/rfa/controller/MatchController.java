package edu.depaul.cdm.se452.rfa.controller;

import edu.depaul.cdm.se452.rfa.repository.RoommateMatchesRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@RestController()
@RequestMapping("/api/matches")
public class MatchController {
    RoommateMatchesRepository roommateMatchesRepository;

    public MatchController(RoommateMatchesRepository matchesRepository) {
        this.roommateMatchesRepository = matchesRepository;
    }

    @PostMapping("/run")
    public ResponseEntity<?> runMatchingAlgorithm(){
        return ResponseEntity.ok("Protected route data! Yum!!!!");
    }
}
