package com.fatec.glab.repository;

import com.fatec.glab.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmailAndActiveTrue(String email);
}
