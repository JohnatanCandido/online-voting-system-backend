package com.aernaur.votingSystem.repository;

import com.aernaur.votingSystem.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {}
