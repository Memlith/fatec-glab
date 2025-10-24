package com.fatec.glab.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fatec.glab.model.Laboratory;

public interface LaboratoryRepository extends MongoRepository<Laboratory, Object> {

}
