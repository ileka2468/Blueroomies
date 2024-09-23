package edu.depaul.cdm.se452.rfa.service;

import edu.depaul.cdm.se452.rfa.entity.User;
import edu.depaul.cdm.se452.rfa.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // to load user by username
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Get roles from userRoles
        List<SimpleGrantedAuthority> authorities = user.getUserRoles().stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getRoleName()))
                .collect(Collectors.toList());

        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getEnabled(), authorities);
    }
}
