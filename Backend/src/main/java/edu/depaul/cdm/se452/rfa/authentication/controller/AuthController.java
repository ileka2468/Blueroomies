package edu.depaul.cdm.se452.rfa.authentication.controller;

import edu.depaul.cdm.se452.rfa.authentication.entity.Role;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.authentication.entity.UserRole;
import edu.depaul.cdm.se452.rfa.authentication.entity.UserRoleId;
import edu.depaul.cdm.se452.rfa.authentication.service.AuthResponse;
import edu.depaul.cdm.se452.rfa.authentication.payload.LoginRequest;
import edu.depaul.cdm.se452.rfa.authentication.payload.RegisterRequest;
import edu.depaul.cdm.se452.rfa.authentication.payload.TokenType;
import edu.depaul.cdm.se452.rfa.authentication.repository.RoleRepository;
import edu.depaul.cdm.se452.rfa.authentication.repository.UserRepository;
import edu.depaul.cdm.se452.rfa.authentication.repository.UserRoleRepository;
import edu.depaul.cdm.se452.rfa.authentication.security.JwtTokenProvider;
import edu.depaul.cdm.se452.rfa.authentication.util.UserPrincipal;
import edu.depaul.cdm.se452.rfa.invalidatedtokens.service.InvalidateTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Log4j2
@CrossOrigin
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

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    InvalidateTokenService invalidateTokenService;


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
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            AuthResponse authResponse = tokenProvider.getTokens(authentication);
            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authResponse.getAccessToken());
            response.addCookie(authResponse.getRefreshCookie());

            return ResponseEntity.ok().build();

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletResponse response, HttpServletRequest request) {
        if (request.getCookies() == null || request.getCookies().length == 0) {
            System.out.println("BAD REQUEST NO COOKIES");
            return ResponseEntity.badRequest().body("Refresh token is empty");
        }

        String refreshToken = tokenProvider.getRefreshTokenFromCookies(request);

        if (refreshToken != null) {
            boolean isValid = tokenProvider.validateToken(refreshToken);
            if (isValid) {
                try {
                    String usernameFromJWT = tokenProvider.getUsernameFromJWT(refreshToken);
                    UserPrincipal userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(usernameFromJWT);
                    String newAccessToken = tokenProvider.generateToken(userDetails.getUsername(), userDetails.getAuthorities(), TokenType.JWT);
                    response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
                    return ResponseEntity.ok().build();
                } catch (AuthenticationException e) {
                    System.out.println("IN AUTHENTICATION EXCEPTION!");
                    log.error("e: ", e);
                }

            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String refreshToken = tokenProvider.getRefreshTokenFromCookies(request);
        String accessToken = tokenProvider.getJwtFromRequest(request);
        if (accessToken == null || refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( accessToken == null ? "Access token not provided." : "Refresh token not provided.");
        }

        boolean accessTokenValid = tokenProvider.validateToken(accessToken);
        boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);
        boolean tokensDeactivated;

        if (accessTokenValid && refreshTokenValid) {
            tokensDeactivated = invalidateTokenService.invalidateTokens(accessToken, refreshToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access or refresh token");
        }

        if (tokensDeactivated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}

