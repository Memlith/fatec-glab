package com.fatec.glab.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fatec.glab.model.User;

public interface UserRepository extends MongoRepository<User, Object> {

}
