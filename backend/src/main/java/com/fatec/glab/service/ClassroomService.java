package com.fatec.glab.service;

import java.util.List;
import java.util.Optional;

import com.fatec.glab.dto.classroom.ClassroomRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.glab.exception.IdNotFoundException;
import com.fatec.glab.model.Classroom;
import com.fatec.glab.repository.ClassroomRepository;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    public Classroom save(ClassroomRequestDTO classroomDTO) {
        Classroom classroom = new Classroom(classroomDTO);
        return classroomRepository.save(classroom);
    }

    public Optional<Classroom> getById(String id) {
        return classroomRepository.findById(id);
    }

    public List<Classroom> getAll() {
        return classroomRepository.findAll();
    }



    public Classroom update(String id, Classroom updatedClassroom) {
        Optional<Classroom> existingClassroom = classroomRepository.findById(id);
        if (existingClassroom.isPresent()) {
            Classroom classroom = existingClassroom.get();
            classroom.setName(updatedClassroom.getName());
            classroom.setCapacity(updatedClassroom.getCapacity());
            classroom.setEquipments(updatedClassroom.getEquipments());
            classroom.setSoftwares(updatedClassroom.getSoftwares());
            return classroomRepository.save(classroom);
        } else {
            throw new IdNotFoundException("Classroom com ID " + id + " não encontrado.");

        }
    }

    public void delete(String id) {
        classroomRepository.deleteById(id);
    }

}
