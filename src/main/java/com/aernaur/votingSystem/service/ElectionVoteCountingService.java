package com.aernaur.votingSystem.service;

import com.aernaur.votingSystem.entity.EncryptedVote;
import com.aernaur.votingSystem.entity.Vote;
import com.aernaur.votingSystem.exceptions.EncryptionException;
import com.aernaur.votingSystem.repository.EncryptedVoteRepository;
import com.aernaur.votingSystem.repository.VoteRepository;
import com.aernaur.votingSystem.service.queue.QueueManager;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ElectionVoteCountingService {

    private final QueueManager queueManager;
    private final EncryptionService encryptionService;
    private final EncryptedVoteRepository encryptedVoteRepository;
    private final VoteRepository voteRepository;

    public ElectionVoteCountingService(QueueManager queueManager,
                                       EncryptionService encryptionService,
                                       EncryptedVoteRepository encryptedVoteRepository,
                                       VoteRepository voteRepository) {
        this.queueManager = queueManager;
        this.encryptionService = encryptionService;
        this.encryptedVoteRepository = encryptedVoteRepository;
        this.voteRepository = voteRepository;
    }

    public void countVotes(UUID subElectionId) {
        for (EncryptedVote encryptedVote: encryptedVoteRepository.findAllBySubElectionId(subElectionId)) {
            queueManager.sendDecryptAndSaveVote(encryptedVote);
        }
    }

    public void decryptAndSaveVote(EncryptedVote encryptedVote) throws EncryptionException {
        Vote vote = new Vote();
        vote.setSubElectionId(encryptedVote.getSubElectionId());
        vote.setEncryptedVoteId(encryptedVote.getId());

        String candidateId = encryptionService.decrypt(encryptedVote.getEncryptedData());
        if (!candidateId.equals("invalid")) {
            vote.setCandidateId(UUID.fromString(candidateId));
        }
        voteRepository.save(vote);
    }
}
