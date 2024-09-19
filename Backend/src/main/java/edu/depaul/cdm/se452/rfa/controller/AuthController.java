package edu.depaul.cdm.se452.rfa.controller;

import edu.depaul.cdm.se452.rfa.entity.Role;
import edu.depaul.cdm.se452.rfa.entity.User;
import edu.depaul.cdm.se452.rfa.entity.UserRole;
import edu.depaul.cdm.se452.rfa.entity.UserRoleId;
import edu.depaul.cdm.se452.rfa.payload.AuthResponse;
import edu.depaul.cdm.se452.rfa.payload.LoginRequest;
import edu.depaul.cdm.se452.rfa.payload.RegisterRequest;
import edu.depaul.cdm.se452.rfa.repository.RoleRepository;
import edu.depaul.cdm.se452.rfa.repository.UserRepository;
import edu.depaul.cdm.se452.rfa.repository.UserRoleRepository;
import edu.depaul.cdm.se452.rfa.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // User Registration
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        // Create new user
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEnabled(true);

        userRepository.save(user);

        // Assign ROLE_USER
        Role userRole = roleRepository.findByRoleName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setRoleName("ROLE_USER");
            roleRepository.save(userRole);
        }

        UserRole userRoleLink = new UserRole();
        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setUserId(user.getId());
        userRoleId.setRoleId(userRole.getId());
        userRoleLink.setId(userRoleId);
        userRoleLink.setUser(user);
        userRoleLink.setRole(userRole);

        userRoleRepository.save(userRoleLink);

        return ResponseEntity.ok("User registered successfully");
    }

    // User Login
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            String jwt = tokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new AuthResponse(jwt));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
