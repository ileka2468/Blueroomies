package edu.depaul.cdm.se452.rfa.authentication.service;

import edu.depaul.cdm.se452.rfa.invalidatedtokens.service.InvalidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenValidationService {
    @Autowired
    private InvalidateTokenService invalidateTokenService;

    public boolean isTokenBlacklisted(String token) {
        return invalidateTokenService.loadTokenInvalidatedByJWT(token) != null;
    }
}
