package com.fatec.glab.model;


import com.fatec.glab.dto.user.UserCreateRequestDTO;
import lombok.Getter;
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

    @Getter
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private Boolean active;

    public User(UserCreateRequestDTO userRequestDTO, String encryptedPassword) {
        this.name = userRequestDTO.name();
        this.email = userRequestDTO.email();
        this.password = encryptedPassword;
        this.active = true;

    }


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
