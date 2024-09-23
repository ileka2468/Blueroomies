package edu.depaul.cdm.se452.rfa.security;

import edu.depaul.cdm.se452.rfa.entity.Invalidatedtoken;
import edu.depaul.cdm.se452.rfa.repository.InvalidatedTokensRepository;
import edu.depaul.cdm.se452.rfa.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import org.apache.commons.codec.digest.DigestUtils;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private InvalidatedTokensRepository invalidatedTokensRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = getJwtFromRequest(request);
        Invalidatedtoken invalidatedToken = null;

        if (jwt != null){
            String tokenHash = DigestUtils.sha256Hex(jwt);
            invalidatedToken = invalidatedTokensRepository.findByTokenHash(tokenHash);
        }

        if (jwt != null && tokenProvider.validateToken(jwt) && invalidatedToken == null) {
            System.out.println("JWT Provided, Token Verified, Token Valid");
            String username = tokenProvider.getUsernameFromJWT(jwt);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    // Helper method to get token from request
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
