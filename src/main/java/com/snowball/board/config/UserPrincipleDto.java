package com.snowball.board.config;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This Class is for set AuthenticationToken of UserDetails
 * Minimum Information of user
 */
@Data
public class UserPrincipleDto implements UserDetails {

    private Long id; // unique id from user_tb(primary key)
    private List<String> userRole;

    public UserPrincipleDto(Long id, List<String> userRole) {
        this.id = id;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String str : this.userRole) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + str));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
