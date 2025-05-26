package com.example.fullstackbackend.repository;

import com.example.fullstackbackend.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentificationRepository extends JpaRepository<AuthUser, Long> {
    AuthUser findByLogin(String Login);
}
