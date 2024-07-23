package com.example.JuegoDeDadosMongodbPersistence.Repository;

import com.example.JuegoDeDadosMongodbPersistence.Model.RoleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRole(RoleEntity.ERole role);
}
