package com.fatec.glab.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fatec.glab.model.Classroom;

public interface ClassroomRepository extends MongoRepository<Classroom, Object> {

}
