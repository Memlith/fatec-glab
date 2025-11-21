package com.fatec.glab.repository;

import com.fatec.glab.model.Software;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SoftwareRepository extends MongoRepository<Software, String> {
}
