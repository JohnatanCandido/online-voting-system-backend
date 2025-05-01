package com.aernaur.votingSystem.repository;

import com.aernaur.votingSystem.entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ElectionRepository extends JpaRepository<Election, UUID> {}
