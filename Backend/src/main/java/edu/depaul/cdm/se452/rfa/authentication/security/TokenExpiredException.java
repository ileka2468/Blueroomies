package edu.depaul.cdm.se452.rfa.authentication.security;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}