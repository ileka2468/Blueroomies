package edu.depaul.cdm.se452.rfa.roomate.service;

import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MatchResponse {
    private List<RoommateMatch> matchList;

    public MatchResponse(List<RoommateMatch> matchList) {
        this.matchList = matchList;
    }
}
