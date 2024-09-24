package edu.depaul.cdm.se452.rfa.authentication.repository;

import edu.depaul.cdm.se452.rfa.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}
