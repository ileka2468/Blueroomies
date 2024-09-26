package edu.depaul.cdm.se452.rfa.profilemanagement.repository;

import edu.depaul.cdm.se452.rfa.profilemanagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    public Profile findProfileByUser(User user);
}
