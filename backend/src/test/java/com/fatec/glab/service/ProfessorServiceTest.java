package com.fatec.glab.service;

import java.util.Optional;

import com.fatec.glab.dto.professor.ProfessorRequestDTO;
import com.fatec.glab.dto.professor.ProfessorRequestUpdateDTO;
import com.fatec.glab.mapper.ProfessorMapper;
import com.fatec.glab.model.Professor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fatec.glab.dto.professor.ProfessorResponseDTO;
import com.fatec.glab.repository.ProfessorRepository;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {

    @InjectMocks
    private ProfessorService professorService;

    @Mock
    ProfessorRepository professorRepository;

    @Mock
    Professor professor;

    @Mock
    ProfessorMapper professorMapper;

    ProfessorResponseDTO dto;
    ProfessorRequestDTO dtoRequest;
    ProfessorRequestUpdateDTO dtoRequestUpdate;

    @Test
    void deveriaCriarUsuario() {
        //Arrange
        this.professor =  new Professor("1", "Caio", "teste@teste.com");
        this.dtoRequest = new ProfessorRequestDTO("Caio", "teste@teste.com");
        given(professorMapper.toEntity(dtoRequest)).willReturn(professor);
        //Act
        professorService.save(dtoRequest);
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
        this.dto = new ProfessorResponseDTO("1", "Caio", "teste@teste.com");
        given(professorRepository.findById("1")).willReturn(Optional.of(professor));
        //Act
        professorService.getById("1");
        //Assert
        then(professorRepository).should().findById("1");
    }

    @Test
    void deveriaAtualizarUsuario() {
        this.dto = new ProfessorResponseDTO("1", "Caio", "teste@teste.com");
        this.dtoRequestUpdate = new ProfessorRequestUpdateDTO("Teste", "email@gmai.com");
        given(professorRepository.findById("1")).willReturn(Optional.of(professor));
        given(professorRepository.save(professor)).willReturn(professor);
        //Act
        professorService.update("1", dtoRequestUpdate);
        //Assert
        then(professorRepository).should().save(professor);
    }
}
