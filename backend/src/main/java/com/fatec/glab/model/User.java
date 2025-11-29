package com.fatec.glab.model;


import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@NoArgsConstructor
@Document("Users")
public class User implements UserDetails {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private Boolean active;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void disable() {
        this.active = false;
    }

    public void enable() {
        this.active = true;
    }
}
