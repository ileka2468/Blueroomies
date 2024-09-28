package edu.depaul.cdm.se452.rfa.authentication.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthDataResponse {
    private String username;
    private String firstname;
    private String lastname;
    private String pfp;
}
