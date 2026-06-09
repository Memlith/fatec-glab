package com.fatec.glab.controller;

import com.fatec.glab.config.FirebaseConfig;
import com.fatec.glab.dto.user.UserCreateRequestDTO;
import com.fatec.glab.dto.user.UserResponseDTO;
import com.fatec.glab.mapper.UserMapper;
import com.fatec.glab.model.User;
import com.fatec.glab.service.UserService;
import com.fatec.glab.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private UserMapper userMapper;

    @Test
    void shouldReturn201AndLocationHeaderWhenRegisteringUser() throws Exception {
        // Arrange
        String jsonRequest = """
                {
                    "name": "Caio",
                    "email": "teste@email.com",
                    "password": "senha123"
                }
                """;

        UserCreateRequestDTO mockRequestDTO = new UserCreateRequestDTO("Caio", "teste@email.com", "senha123");
        
        User mockUser = new User(mockRequestDTO, "senha-criptografada");
        mockUser.setId("999");

        UserResponseDTO mockResponseDTO = new UserResponseDTO("999", "Caio", "teste@email.com", "USER");

        given(userService.registerUser(any(UserCreateRequestDTO.class))).willReturn(mockUser);
        given(userMapper.toUserResponseDTO(any(User.class))).willReturn(mockResponseDTO);

        // Act & Assert
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/users/999"));
    }
}