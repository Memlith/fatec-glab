package com.fatec.glab.service;

import com.fatec.glab.dto.user.UserCreateRequestDTO;
import com.fatec.glab.exception.UserAlreadyExist;
import com.fatec.glab.model.User;
import com.fatec.glab.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void deveriaRegistrarNovoUsuarioComSucesso() {
        // Arrange
        UserCreateRequestDTO dtoRequest = new UserCreateRequestDTO("Caio", "teste@teste.com", "senha123");
        
        given(userRepository.findByEmailIgnoreCase(dtoRequest.email())).willReturn(Optional.empty());
        given(passwordEncoder.encode(dtoRequest.password())).willReturn("senha-criptografada");
        
        User userSalvo = new User(dtoRequest, "senha-criptografada");
        userSalvo.setId("12345");
        given(userRepository.save(any(User.class))).willReturn(userSalvo);

        // Act
        User resultado = userService.createUserWithEmailAndPassword(dtoRequest);

        // Assert
        assertNotNull(resultado.getId());
        assertEquals("senha-criptografada", resultado.getPassword());
        assertTrue(resultado.getActive());
        
        then(passwordEncoder).should().encode("senha123");
        then(userRepository).should().save(any(User.class));
    }

    @Test
    void deveriaLancarExcecaoQuandoEmailJaExiste() {
        // Arrange
        UserCreateRequestDTO dtoRequest = new UserCreateRequestDTO("Caio", "teste@teste.com", "senha123");
        User usuarioExistente = new User(dtoRequest, "senha-antiga");
        
        given(userRepository.findByEmailIgnoreCase(dtoRequest.email())).willReturn(Optional.of(usuarioExistente));

        // Act & Assert
        assertThrows(UserAlreadyExist.class, () -> {
            userService.createUserWithEmailAndPassword(dtoRequest);
        });

        then(userRepository).should(never()).save(any(User.class));
    }
}