package com.fatec.glab.model;

import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;

import com.fatec.glab.dto.user.UserCreateRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Document("users")
public class User implements UserDetails {


    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private Boolean active;
    @CreatedDate
    private Instant createdDate;

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
