package com.aernaur.votingSystem.repository;

import com.aernaur.votingSystem.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, UUID> {}
