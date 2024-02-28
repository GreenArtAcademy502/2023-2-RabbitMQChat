package com.green.rabbitmqchat.security;


import com.green.rabbitmqchat.user.model.UserModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class MyUserDetails implements UserDetails {

    private MyPrincipal myPrincipal;
    private Map<String, Object> attributes;
    private UserModel userModel;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(myPrincipal == null) {
            return null;
        }
        return this.myPrincipal.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return null; }

    @Override
    public String getUsername() { return userModel == null ? null : userModel.getUid(); }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
