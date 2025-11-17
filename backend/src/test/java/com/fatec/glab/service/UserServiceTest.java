package com.fatec.glab.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fatec.glab.dto.user.UserResponseDTO;
import com.fatec.glab.model.User;
import com.fatec.glab.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    User user;

    UserResponseDTO dto;

    @Test
    void deveriaCriarUsuario() {
        //Arrange
        this.dto = new UserResponseDTO("1", "Caio", "teste@teste.com", "admin");
        given(userRepository.save(user)).willReturn(user);
        //Act
        userService.save(user);
        //Assert
        then(userRepository).should().save(user);
    }

    @Test
    void deveriaListarUsuarios() {
        //Arrange
        //Act
        userService.getAll();
        //Assert
        then(userRepository).should().findAll();
    }

    @Test
    void deveriaListarUsuarioPorId() {
        //Arrange
        this.dto = new UserResponseDTO("1", "Caio", "teste@teste.com", "admin");
        given(userRepository.findById("1")).willReturn(Optional.of(user));
        //Act
        userService.getById("1");
        //Assert
        then(userRepository).should().findById("1");
    }

    @Test
    void deveriaAtualizarUsuario() {
        this.dto = new UserResponseDTO("1", "Caio", "teste@teste.com", "admin");
        given(userRepository.findById("1")).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);
        //Act
        userService.update("1", user);
        //Assert
        then(userRepository).should().save(user);
    }
}
