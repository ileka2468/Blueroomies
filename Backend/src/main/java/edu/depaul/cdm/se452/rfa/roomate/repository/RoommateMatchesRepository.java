package edu.depaul.cdm.se452.rfa.roomate.repository;

import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.roomate.entity.RoommateMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoommateMatchesRepository extends JpaRepository<RoommateMatch, Integer> {

    @Query("SELECT rm FROM RoommateMatch rm WHERE rm.userId1 = :user OR rm.userId2 = :user")
    List<RoommateMatch> findMatchesByUser(@Param("user") User user);

}

