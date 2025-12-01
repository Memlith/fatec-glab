package com.fatec.glab.repository;

import com.fatec.glab.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmailIgnoreCaseAndActiveTrue (String email);

    Boolean findByEmailIgnoreCase(@NotBlank String email);
}
