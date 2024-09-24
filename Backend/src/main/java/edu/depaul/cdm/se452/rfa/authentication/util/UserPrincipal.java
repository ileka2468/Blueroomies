package edu.depaul.cdm.se452.rfa.authentication.util;

import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
public class UserPrincipal implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    private List<? extends GrantedAuthority> authorities;

    public UserPrincipal(Integer id, String username, String password, Boolean enabled,
                         List<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true; // TODO: actually implement
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // TODO: actually implement
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // TODO: actually implement
    }

    @Override
    public boolean isEnabled() {
        return enabled; // TODO: actually implement
    }
}
