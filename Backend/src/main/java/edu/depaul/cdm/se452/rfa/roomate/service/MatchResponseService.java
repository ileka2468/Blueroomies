package edu.depaul.cdm.se452.rfa.roomate.service;

import edu.depaul.cdm.se452.rfa.profileManagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
import edu.depaul.cdm.se452.rfa.roomate.repository.RoommateMatchesRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class MatchResponseService {
    @Getter
    @Setter
    public static class MatchDTO {
        private int userID;
        private BigDecimal matchScore;
        private ProfileDTO profile;

        public MatchDTO(int userID, BigDecimal matchScore, ProfileDTO profile) {
            this.userID = userID;
            this.matchScore = matchScore;
            this.profile = profile;
        }
    }

    @Getter
    @Setter
    public static class ProfileDTO {
        private String name;
        private String bio;
        private Map<String, Object> characteristics;

        public ProfileDTO(Profile profile) {
            this.name = profile.getUser().getFirstName() + " " + profile.getUser().getLastName();
            this.bio = profile.getBio();
        }
    }
}
