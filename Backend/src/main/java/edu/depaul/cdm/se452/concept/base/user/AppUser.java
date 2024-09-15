package edu.depaul.cdm.se452.concept.base.user;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    private Integer userid;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private Set<UserRole> authorities;

}
