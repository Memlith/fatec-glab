package com.fatec.glab.controller;

import com.fatec.glab.dto.authentication.LoginRequestDTO;
import com.fatec.glab.dto.token.TokenData;
import com.fatec.glab.model.User;
import com.fatec.glab.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenData> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());
        var authentication = authenticationManager.authenticate(authenticationToken);

        String accessToken = tokenService.generatedToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenData(accessToken));
    }

}
