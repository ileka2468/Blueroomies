package edu.depaul.cdm.se452.rfa.profileManagement.repository;
import edu.depaul.cdm.se452.rfa.profileManagement.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    // You can add custom query methods here, if needed
    Profile findByUserId(Long userId);
}

