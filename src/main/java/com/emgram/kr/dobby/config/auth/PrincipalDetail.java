package com.emgram.kr.dobby.config.auth;

import com.emgram.kr.dobby.dto.login.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


@Getter
@Data
public class PrincipalDetail implements UserDetails {

    private User user;

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Collection<GrantedAuthority> collectors = new ArrayList<>();
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        user.getRoleList().forEach(r->{
            System.out.println("PrincipalDetail에서 r: "+r);
            authorities.add(()->r);
        });
        return authorities;
    }
}
