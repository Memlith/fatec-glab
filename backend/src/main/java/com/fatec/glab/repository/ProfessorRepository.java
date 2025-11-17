package com.fatec.glab.repository;

import com.fatec.glab.model.Professor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfessorRepository extends MongoRepository<Professor, Object> {

}
