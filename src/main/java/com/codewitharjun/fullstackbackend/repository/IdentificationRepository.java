package com.codewitharjun.fullstackbackend.repository;

import com.codewitharjun.fullstackbackend.model.Identification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentificationRepository extends JpaRepository<Identification, Long> {
    Identification findByLogin(String Login);
}
