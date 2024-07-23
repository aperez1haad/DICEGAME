package com.example.JuegoDeDadosMongodbPersistence.Repository;

import com.example.JuegoDeDadosMongodbPersistence.Model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
    void deleteByEmail(String email);
    Optional<UserEntity> findByName(String name);
}
