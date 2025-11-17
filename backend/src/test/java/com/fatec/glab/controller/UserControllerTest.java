package com.fatec.glab.controller;

import com.fatec.glab.dto.user.UserResponseDTO;
import com.fatec.glab.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnStatus200WhenListingAllAvailableUsers() throws Exception {
        //Arrange
        BDDMockito.given(userService.getAll()).willReturn(List.of(
                new UserResponseDTO("1", "AA", "AA@example.com", "AAAA"),
                new UserResponseDTO("2", "BB", "BB@example.com", "AAAA")
        ));

        //Act
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        assertEquals(200, response.getStatus());
    }

}