package edu.depaul.cdm.se452.rfa.service;

import jakarta.servlet.http.Cookie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class AuthResponse {
    private String accessToken;
    private Cookie refreshCookie;

    public AuthResponse(String accessToken, Cookie refreshCookie) {
        this.accessToken = accessToken;
        this.refreshCookie = refreshCookie;
    }
}
