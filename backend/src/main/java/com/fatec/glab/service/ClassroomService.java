package com.fatec.glab.service;

import java.util.List;

import com.fatec.glab.dto.classroom.ClassroomRequestDTO;
import com.fatec.glab.dto.classroom.ClassroomRequestUpdateDTO;
import com.fatec.glab.mapper.ClassroomMapper;
import com.fatec.glab.model.Classroom;
import com.fatec.glab.repository.ClassroomRepository;
import com.fatec.glab.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private ClassroomMapper classroomMapper;

    @Transactional
    public Classroom save(ClassroomRequestDTO classroomDTO) {
        Classroom classroom = classroomMapper.toEntity(classroomDTO);
        return classroomRepository.save(classroom);
    }

    public Classroom getById(String id) {
        return classroomRepository.findById(id)
                .orElseThrow(() ->
                        new IdNotFoundException("Classroom com ID " + id + " não foi encontrado.")
                );
    }

    public List<Classroom> getAll() {
        return classroomRepository.findAll();
    }

    @Transactional
    public Classroom update(String id, ClassroomRequestUpdateDTO dto) {
        Classroom existingClassroom = classroomRepository.findById(id)
                .orElseThrow(() ->
                        new IdNotFoundException("Classroom com ID " + id + " não foi encontrado.")
                );

        classroomMapper.updateFromDTO(dto, existingClassroom);

        return classroomRepository.save(existingClassroom);
    }

    @Transactional
    public void delete(String id) {
        classroomRepository.deleteById(id);
    }
}
