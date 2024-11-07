package edu.depaul.cdm.se452.rfa.roomate.repository;

import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoommateMatchesRepository extends JpaRepository<RoommateMatch, Integer> {
    public RoommateMatch findRoommateMatchById(int id);
    List<RoommateMatch> findByUserId(int userId);
}
