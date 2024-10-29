package edu.depaul.cdm.se452.rfa.messaging.security;

import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.authentication.security.JwtTokenProvider;
import edu.depaul.cdm.se452.rfa.authentication.service.CustomUserDetailsService;
import edu.depaul.cdm.se452.rfa.messaging.payload.SocketsAuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/messages/security")
public class MessagesAuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public MessagesAuthController(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateSocket(HttpServletRequest request) {

        String accessToken = jwtTokenProvider.getJwtFromRequest(request);
        String username = null;

        if (accessToken != null) {
            username = jwtTokenProvider.getUsernameFromJWT(accessToken);
        }

        if (accessToken == null || username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean accessTokenValid = jwtTokenProvider.validateToken(accessToken);

        if (!accessTokenValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = customUserDetailsService.getUserByUsername(username);
        return ResponseEntity.ok(new SocketsAuthResponse(user.getId(), user.getUsername(), user.getUserRoles()));
    }
}