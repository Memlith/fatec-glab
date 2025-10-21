package com.fatec.glab.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fatec.glab.model.Equipment;

public interface EquipmentRepository extends MongoRepository<Equipment, Object> {

}
