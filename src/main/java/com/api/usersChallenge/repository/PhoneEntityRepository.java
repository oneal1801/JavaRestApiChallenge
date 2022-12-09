package com.api.usersChallenge.repository;

import com.api.usersChallenge.models.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneEntityRepository extends JpaRepository<PhoneEntity, UUID> {
}