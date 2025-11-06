package com.fatec.glab.controller;

import java.util.List;
import java.util.Optional;

import com.fatec.glab.dto.UserResponseDTO;
import com.fatec.glab.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.glab.model.User;
import com.fatec.glab.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAll()) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable String id) {

        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getById(id));

    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody User user) {
        UserResponseDTO userResponseDTO = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable String id, @RequestBody User user) {

        return ResponseEntity.status(HttpStatus.OK).body(this.userService.update(id, user));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {

        this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deletado com sucesso.");

    }
}
