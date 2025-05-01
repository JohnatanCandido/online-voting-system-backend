package com.aernaur.votingSystem.dto;

import java.util.UUID;

public record SearchCandidateDTO(UUID candidateId,
                                 UUID personId,
                                 UUID partyId,
                                 UUID subElectionId,
                                 Integer number,
                                 String candidateName) {
}
