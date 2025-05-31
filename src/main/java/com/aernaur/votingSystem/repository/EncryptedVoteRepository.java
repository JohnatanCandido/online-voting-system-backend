package com.aernaur.votingSystem.repository;

import com.aernaur.votingSystem.entity.EncryptedVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EncryptedVoteRepository extends JpaRepository<EncryptedVote, UUID> {

    @Query("""
        SELECT encryptedVote
        FROM EncryptedVote encryptedVote
        WHERE encryptedVote.subElectionId = :subElectionId
        """)
    List<EncryptedVote> findAllBySubElectionId(@Param("electionId") UUID subElectionId);
}
