package com.fatec.glab.service;

import java.util.Optional;

import com.fatec.glab.model.Professor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fatec.glab.dto.user.ProfessorResponseDTO;
import com.fatec.glab.repository.ProfessorRepository;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {

    @InjectMocks
    private ProfessorService professorService;

    @Mock
    ProfessorRepository professorRepository;

    @Mock
    Professor professor;

    ProfessorResponseDTO dto;

    @Test
    void deveriaCriarUsuario() {
        //Arrange
        this.dto = new ProfessorResponseDTO("1", "Caio", "teste@teste.com", "admin");
        given(professorRepository.save(professor)).willReturn(professor);
        //Act
        professorService.save(professor);
        //Assert
        then(professorRepository).should().save(professor);
    }

    @Test
    void deveriaListarUsuarios() {
        //Arrange
        //Act
        professorService.getAll();
        //Assert
        then(professorRepository).should().findAll();
    }

    @Test
    void deveriaListarUsuarioPorId() {
        //Arrange
        this.dto = new ProfessorResponseDTO("1", "Caio", "teste@teste.com", "admin");
        given(professorRepository.findById("1")).willReturn(Optional.of(professor));
        //Act
        professorService.getById("1");
        //Assert
        then(professorRepository).should().findById("1");
    }

    @Test
    void deveriaAtualizarUsuario() {
        this.dto = new ProfessorResponseDTO("1", "Caio", "teste@teste.com", "admin");
        given(professorRepository.findById("1")).willReturn(Optional.of(professor));
        given(professorRepository.save(professor)).willReturn(professor);
        //Act
        professorService.update("1", professor);
        //Assert
        then(professorRepository).should().save(professor);
    }
}
