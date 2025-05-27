package com.aernaur.votingSystem.service;

import com.aernaur.votingSystem.dto.VoteRequestDTO;
import com.aernaur.votingSystem.dto.VoteRequestWrapper;
import com.aernaur.votingSystem.entity.EncryptedVote;
import com.aernaur.votingSystem.exceptions.EncryptionException;
import com.aernaur.votingSystem.repository.CandidateRepository;
import com.aernaur.votingSystem.repository.EncryptedVoteRepository;
import com.aernaur.votingSystem.service.queue.QueueManager;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoteService {

    private final QueueManager queueManager;
    private final EncryptionService encryptionService;
    private final EncryptedVoteRepository encryptedVoteRepository;
    private final CandidateRepository candidateRepository;

    public VoteService(QueueManager queueManager,
                       EncryptionService encryptionService,
                       EncryptedVoteRepository encryptedVoteRepository,
                       CandidateRepository candidateRepository) {
        this.queueManager = queueManager;
        this.encryptionService = encryptionService;
        this.encryptedVoteRepository = encryptedVoteRepository;
        this.candidateRepository = candidateRepository;
    }

    public void saveVotes(VoteRequestWrapper voteWrapper) {
        for (VoteRequestDTO vote: voteWrapper.votes()) {
            queueManager.sendRegisterVote(vote);
        }
    }

    public void saveVote(VoteRequestDTO voteRequestDTO) throws EncryptionException {
        EncryptedVote vote = new EncryptedVote();
        vote.setSubElectionId(voteRequestDTO.subElectionId());
        String candidateId = findCandidateId(voteRequestDTO);
        vote.setEncryptedData(encryptionService.encrypt(candidateId));
        encryptedVoteRepository.save(vote);
    }

    private String findCandidateId(VoteRequestDTO voteRequestDTO) {
        UUID candidateId = candidateRepository.findCandidateId(voteRequestDTO.subElectionId(), voteRequestDTO.candidateNumber());
        if (candidateId == null) {
            return "invalid";
        }
        return candidateId.toString();
    }
}
