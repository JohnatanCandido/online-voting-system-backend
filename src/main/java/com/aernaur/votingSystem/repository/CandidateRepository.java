package com.aernaur.votingSystem.repository;

import com.aernaur.votingSystem.entity.Candidate;
import com.aernaur.votingSystem.dto.CandidateDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    @Query("""
            SELECT new com.aernaur.votingSystem.dto.CandidateDTO(
                candidate.id,
                candidate.number,
                person.name,
                party.name
            )
            FROM Candidate candidate
            JOIN candidate.person person
            JOIN candidate.party party
            WHERE candidate.subElection.id = :subElectionId
            """)
    List<CandidateDTO> searchCandidates(@Param("electionId") UUID subElectionId);
}
