package com.aernaur.votingSystem.service;

import com.aernaur.votingSystem.dto.SearchCandidateDTO;
import com.aernaur.votingSystem.entity.Candidate;
import com.aernaur.votingSystem.entity.Party;
import com.aernaur.votingSystem.entity.Person;
import com.aernaur.votingSystem.entity.SubElection;
import com.aernaur.votingSystem.dto.CandidateDTO;
import com.aernaur.votingSystem.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public List<CandidateDTO> searchCandidates(SearchCandidateDTO filters) {
        return candidateRepository.searchCandidates(filters.subElectionId());
    }

    public UUID saveCandidate(CandidateDTO candidateDTO) {
        Candidate candidate;
        if (candidateDTO.getId() == null) {
            candidate = new Candidate();
            candidate.setPerson(new Person(candidateDTO.getPersonId()));
            candidate.setParty(new Party(candidateDTO.getPartyId()));
            candidate.setSubElection(new SubElection(candidateDTO.getSubElectionId()));
        } else {
            candidate = candidateRepository.findById(candidateDTO.getId()).orElseThrow();
        }
        candidate.setNumber(candidateDTO.getNumber());
        candidate = candidateRepository.save(candidate);
        return candidate.getId();
    }

    public CandidateDTO findCandidate(UUID subElectionId, Integer candidateNumber) {
        return candidateRepository.findCandidate(subElectionId, candidateNumber);
    }
}
