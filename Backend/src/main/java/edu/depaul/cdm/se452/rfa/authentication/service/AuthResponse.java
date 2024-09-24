package edu.depaul.cdm.se452.rfa.authentication.service;

import jakarta.servlet.http.Cookie;
import lombok.Getter;
import lombok.Setter;

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
