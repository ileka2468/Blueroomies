package edu.depaul.cdm.se452.concept.base.user;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRepository {
    private static HashMap<String, UserRole> ROLES = new HashMap<String, UserRole>();
    static {        
    }

    public UserRole findByAuthority(String authority) {
        return ROLES.get(authority);
    }

    public UserRole save(UserRole userRole) {
        ROLES.put(userRole.getAuthority(), userRole);
        return userRole;
    }    
}
