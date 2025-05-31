package com.aernaur.votingSystem.service;

import com.aernaur.votingSystem.entity.Election;
import com.aernaur.votingSystem.entity.SubElection;
import com.aernaur.votingSystem.dto.ElectionDTO;
import com.aernaur.votingSystem.dto.SubElectionDTO;
import com.aernaur.votingSystem.exceptions.EntityNotFoundException;
import com.aernaur.votingSystem.repository.ElectionRepository;
import com.aernaur.votingSystem.repository.SubElectionRepository;
import com.aernaur.votingSystem.service.queue.QueueManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ElectionService {

    private final QueueManager queueManager;
    private final ElectionRepository electionRepository;
    private final SubElectionRepository subElectionRepository;

    public UUID saveElection(ElectionDTO electionDTO) {
        Election election;
        if (electionDTO.getElectionId() == null) {
            election = new Election();
        } else {
            election = electionRepository.findById(electionDTO.getElectionId()).orElseThrow();
        }
        election.setName(electionDTO.getName());
        election.setDescription(electionDTO.getDescription());
        election.setStartDate(electionDTO.getStart());
        election.setEndDate(electionDTO.getEnd());
        election = electionRepository.save(election);

        return election.getId();
    }

    public UUID saveSubElection(UUID electionId, SubElectionDTO subElectionDTO) {
        SubElection subElection;
        if (subElectionDTO.getId() == null) {
            subElection = new SubElection(new Election(electionId));
        } else {
            subElection = subElectionRepository.findById(subElectionDTO.getId()).orElseThrow();
        }
        subElection.setPosition(subElectionDTO.getPosition());
        subElection.setType(subElectionDTO.getType());
        subElection = subElectionRepository.save(subElection);

        return subElection.getId();
    }

    public List<ElectionDTO> listElections() {
        return electionRepository.findAll().stream().map(ElectionDTO::new).toList();
    }

    public ElectionDTO getElection(UUID id) throws EntityNotFoundException {
        Election election = electionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("election", id));
        var dto = new ElectionDTO(election);
        dto.setSubElections(election.getSubElections().stream().map(SubElectionDTO::new).toList());
        return dto;
    }

    public List<SubElectionDTO> listSubElections(UUID electionId) {
        return subElectionRepository.listSubElections(electionId);
    }

    public void countVotes(UUID electionId) {
        Election election = electionRepository.findById(electionId).orElseThrow();
        for (SubElection subElection : election.getSubElections()) {
            queueManager.sendCountVotes(subElection.getId());
        }
    }
}
