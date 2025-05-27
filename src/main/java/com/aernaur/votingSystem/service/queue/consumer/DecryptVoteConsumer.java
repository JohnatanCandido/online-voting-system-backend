package com.aernaur.votingSystem.service.queue.consumer;

import com.aernaur.votingSystem.entity.EncryptedVote;
import com.aernaur.votingSystem.exceptions.EncryptionException;
import com.aernaur.votingSystem.service.ElectionVoteCountingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class DecryptVoteConsumer {

    private final ElectionVoteCountingService electionVoteCountingService;

    public DecryptVoteConsumer(ElectionVoteCountingService electionVoteCountingService) {
        this.electionVoteCountingService = electionVoteCountingService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.vote.decrypt}"})
    public void consume(EncryptedVote encryptedVote) throws EncryptionException {
        electionVoteCountingService.decryptAndSaveVote(encryptedVote);
    }
}
