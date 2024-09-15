package edu.depaul.cdm.se452.concept.base.user;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository  {
    private static HashMap<String, AppUser> USERS = new HashMap<String, AppUser>();
    static {        
    }

    public AppUser findByUsername(String username) {
        return USERS.get(username);
    }

    public AppUser save(AppUser user) {
        USERS.put(user.getUsername(), user);
        return user;
    }
}
