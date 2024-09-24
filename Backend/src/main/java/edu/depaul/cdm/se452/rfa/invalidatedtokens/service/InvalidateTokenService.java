package edu.depaul.cdm.se452.rfa.invalidatedtokens.service;

import edu.depaul.cdm.se452.rfa.authentication.security.JwtTokenProvider;
import edu.depaul.cdm.se452.rfa.invalidatedtokens.entity.Invalidatedtoken;
import edu.depaul.cdm.se452.rfa.invalidatedtokens.repository.InvalidatedTokensRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class InvalidateTokenService {
    @Autowired
    private InvalidatedTokensRepository invalidatedTokensRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public Invalidatedtoken loadTokenInvalidatedByJWT(String token) {
        if (token == null) {
            return null;
        }
        String tokenHash = DigestUtils.sha256Hex(token);
        return invalidatedTokensRepository.findByTokenHash(tokenHash);
    }

    public boolean invalidateTokens(String accessToken, String refreshToken) {
        boolean isValidToken = jwtTokenProvider.validateToken(accessToken);

        if (accessToken != null && isValidToken && refreshToken != null) {
            String accessTokenHash = DigestUtils.sha256Hex(accessToken);
            Date accessTokenExpiration = jwtTokenProvider.getExpirationDateFromJWT(accessToken);
            OffsetDateTime accessTokenInvalidatedAt = OffsetDateTime.now(ZoneOffset.UTC);

            String refreshTokenHash = DigestUtils.sha256Hex(refreshToken);
            Date refreshTokenExpiration = jwtTokenProvider.getExpirationDateFromJWT(refreshToken);
            OffsetDateTime refreshTokenInvalidatedAt = OffsetDateTime.now(ZoneOffset.UTC);

            Invalidatedtoken invalidatedAccessToken = new Invalidatedtoken();
            Invalidatedtoken invalidatedRefreshToken = new Invalidatedtoken();

            // Set access token fields
            invalidatedAccessToken.setTokenHash(accessTokenHash);
            invalidatedAccessToken.setExpiryTime(accessTokenExpiration.toInstant().atOffset(ZoneOffset.UTC));
            invalidatedAccessToken.setInvalidatedAt(accessTokenInvalidatedAt);

            // Set refresh token fields
            invalidatedRefreshToken.setTokenHash(refreshTokenHash);
            invalidatedRefreshToken.setExpiryTime(refreshTokenExpiration.toInstant().atOffset(ZoneOffset.UTC));
            invalidatedRefreshToken.setInvalidatedAt(refreshTokenInvalidatedAt);

            // Save invalidated tokens
            invalidatedTokensRepository.save(invalidatedAccessToken);
            invalidatedTokensRepository.save(invalidatedRefreshToken);

            return true;
        }
        return false;
    }
}
