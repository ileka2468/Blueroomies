package edu.depaul.cdm.se452.rfa.authentication.repository;

import edu.depaul.cdm.se452.rfa.authentication.entity.UserRole;
import edu.depaul.cdm.se452.rfa.authentication.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
