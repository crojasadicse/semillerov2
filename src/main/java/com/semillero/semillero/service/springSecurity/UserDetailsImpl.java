package com.semillero.semillero.service.springSecurity;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.semillero.semillero.models.User;
import com.semillero.semillero.models.UserRols;

public class UserDetailsImpl implements UserDetails {

    private  User user;
    private List<UserRols> userRols;

    public UserDetailsImpl(User user, List<UserRols> userRols) {
        this.user = user;
        this.userRols = userRols;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        if(userRols.size() == 0){
            return List.of();
        }

        var authorities = userRols.stream()
            .map(userRol ->  new SimpleGrantedAuthority(  "ROLE_" + userRol.getRole().getName() )  )
            .toList();

        return authorities;        

    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

}
