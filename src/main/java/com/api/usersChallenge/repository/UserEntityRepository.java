package com.api.usersChallenge.repository;

import com.api.usersChallenge.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findOneByEmail(String email);

    Boolean existsByEmail(String email);
}