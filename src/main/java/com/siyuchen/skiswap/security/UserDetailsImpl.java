package com.siyuchen.skiswap.security;

import com.siyuchen.skiswap.model.Role;
import com.siyuchen.skiswap.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Siyu Chen
 * This class implements the UserDetails, which is a part of Spring Security Core.
 * Implementation of UserDetails interface offers more flexibility and control over user
 * authorization and authentication processes.
 */
public class UserDetailsImpl implements UserDetails {
    private User user;
    private List<Role> roles;

    public UserDetailsImpl(User user, List<Role> roles){
        super();
        this.user = user;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    // use email to identify current user
    public String getUsername() {
        return this.user.getEmail();
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
        return user.isEnabled();
    }
}
