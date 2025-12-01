package com.fatec.glab.controller;


import com.fatec.glab.dto.user.UserCreateRequestDTO;
import com.fatec.glab.dto.user.UserResponseDTO;
import com.fatec.glab.mapper.UserMapper;
import com.fatec.glab.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserCreateRequestDTO userRequestDTO, UriComponentsBuilder uriBuilder) {
        var user = userService.registerUser(userRequestDTO);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(userMapper.toUserResponseDTO(user));
    }


}
