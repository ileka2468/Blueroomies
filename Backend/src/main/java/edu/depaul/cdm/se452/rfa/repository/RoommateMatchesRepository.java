package edu.depaul.cdm.se452.rfa.repository;

import edu.depaul.cdm.se452.rfa.entity.RoommateMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoommateMatchesRepository extends JpaRepository<RoommateMatch, Integer> {
    public RoommateMatch findRoommateMatchById(int id);
}
