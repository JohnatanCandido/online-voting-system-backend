package com.aernaur.votingSystem.repository;

import com.aernaur.votingSystem.entity.SubElection;
import com.aernaur.votingSystem.dto.SubElectionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SubElectionRepository extends JpaRepository<SubElection, UUID> {

    @Query("""
            SELECT new com.aernaur.votingSystem.dto.SubElectionDTO(
                subElection.id,
                subElection.position,
                subElection.type
            )
            FROM SubElection subElection
            WHERE subElection.election.id = :electionId
            """)
    List<SubElectionDTO> listSubElections(@Param("electionId") UUID electionId);
}
