package com.aernaur.votingSystem.repository;

import com.aernaur.votingSystem.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PartyRepository extends JpaRepository<Party, UUID> {}
