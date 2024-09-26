package edu.depaul.cdm.se452.rfa.authentication.security;

import edu.depaul.cdm.se452.rfa.authentication.payload.TokenType;
import edu.depaul.cdm.se452.rfa.authentication.service.AuthResponse;
import edu.depaul.cdm.se452.rfa.authentication.service.CustomUserDetailsService;
import edu.depaul.cdm.se452.rfa.authentication.service.TokenValidationService;
import edu.depaul.cdm.se452.rfa.invalidatedtokens.entity.Invalidatedtoken;
import edu.depaul.cdm.se452.rfa.invalidatedtokens.service.InvalidateTokenService;
import io.jsonwebtoken.*;
import edu.depaul.cdm.se452.rfa.authentication.util.UserPrincipal;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private TokenValidationService tokenValidationService;

    // Generate JWT token
    public String generateToken(Authentication authentication, TokenType tokenType) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        int expiryTime = tokenType.equals(TokenType.JWT) ? jwtExpirationInMs : refreshtokenExpirationInMs;
        Date expiryDate = new Date(now.getTime() + expiryTime);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", userPrincipal.getAuthorities())
                .claim("tokenType", tokenType.name())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateToken(String username, List<? extends GrantedAuthority> authorities, TokenType tokenType) {
        Date now = new Date();
        int expiryTime = tokenType.equals(TokenType.JWT) ? jwtExpirationInMs : refreshtokenExpirationInMs;
        Date expiryDate = new Date(now.getTime() + expiryTime);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", authorities)
                .claim("tokenType", tokenType.name())
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

    public Date getExpirationDateFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    // Validate token
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            if (!tokenValidationService.isTokenBlacklisted(authToken)) {
                return true;
            }
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Invalid JWT signature");
        }
        catch (JwtException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public AuthResponse getTokens(Authentication authentication) {
        String jwt = generateToken(authentication, TokenType.JWT);
        String refreshToken = generateToken(authentication, TokenType.REFRESH_TOKEN);

        assert !jwt.equals(refreshToken);

        // create a cookie with refresh token
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/api/auth/");
        refreshTokenCookie.setMaxAge(getRefreshtokenExpirationInMs() / 1000);

        return new AuthResponse(jwt, refreshTokenCookie);
    }

    public String getRefreshTokenFromCookies(HttpServletRequest request) {

        if (request.getCookies() == null || request.getCookies().length == 0) {
            System.out.println("No cookies in request.");
            return null;
        }
        List<Cookie> requestCookies = List.of(request.getCookies());
        String refreshToken = null;

        for (Cookie requestCookie : requestCookies) {
            if (requestCookie.getName().equals("refresh_token")) {
                refreshToken = requestCookie.getValue();
            }
        }
        return refreshToken;
    }

    // method to get token from request
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
