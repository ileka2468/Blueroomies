package edu.depaul.cdm.se452.rfa.authentication.security;

import edu.depaul.cdm.se452.rfa.authentication.payload.TokenType;
import edu.depaul.cdm.se452.rfa.authentication.service.AuthResponse;
import edu.depaul.cdm.se452.rfa.authentication.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import edu.depaul.cdm.se452.rfa.authentication.util.UserPrincipal;
import jakarta.servlet.http.Cookie;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component
public class JwtTokenProvider {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @Value("${app.refreshtokenExpirationInMs}")
    private int refreshtokenExpirationInMs;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Generate JWT token
    public String generateToken(Authentication authentication, TokenType tokenType) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        int expiryTime = tokenType.equals(TokenType.JWT) ? jwtExpirationInMs : refreshtokenExpirationInMs;
        Date expiryDate = new Date(now.getTime() + expiryTime);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", userPrincipal.getAuthorities())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateToken(String username, List< ? extends GrantedAuthority> authorities, TokenType tokenType) {
        Date now = new Date();
        int expiryTime = tokenType.equals(TokenType.JWT) ? jwtExpirationInMs : refreshtokenExpirationInMs;
        Date expiryDate = new Date(now.getTime() + expiryTime);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", authorities)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Get username from token
    public String getUsernameFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate token
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (JwtException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public AuthResponse getTokens(Authentication authentication) {
        String jwt = generateToken(authentication, TokenType.JWT);
        String refreshToken = generateToken(authentication, TokenType.REFRESH_TOKEN);

        // create a cookie with refresh token
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/api/auth/refresh-token");
        refreshTokenCookie.setMaxAge(getRefreshtokenExpirationInMs() / 1000);

        return new AuthResponse(jwt, refreshTokenCookie);
    }
}
