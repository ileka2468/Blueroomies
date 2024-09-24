package edu.depaul.cdm.se452.rfa.authentication.security;

import edu.depaul.cdm.se452.rfa.invalidatedtokens.entity.Invalidatedtoken;
import edu.depaul.cdm.se452.rfa.authentication.service.CustomUserDetailsService;
import edu.depaul.cdm.se452.rfa.invalidatedtokens.service.InvalidateTokenService;
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


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private InvalidateTokenService invalidateTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = tokenProvider.getJwtFromRequest(request);
        Invalidatedtoken invalidatedtoken = null;

        // check if jwt was extracted from the request. If so do deactivated token search.
        if (jwt != null){
            invalidatedtoken = invalidateTokenService.loadTokenInvalidatedByJWT(jwt);
        }

        // if jwt present, token is validated, and the token hasn't been deactivated authenticate user
        if (jwt != null && tokenProvider.validateToken(jwt) && invalidatedtoken == null) {
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
}
