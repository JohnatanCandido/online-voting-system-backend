package com.aernaur.votingSystem.service.queue.consumer;

import com.aernaur.votingSystem.dto.VoteRequestDTO;
import com.aernaur.votingSystem.exceptions.EncryptionException;
import com.aernaur.votingSystem.service.VoteService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RegisterVoteConsumer {

    private final VoteService voteService;

    public RegisterVoteConsumer(VoteService voteService) {
        this.voteService = voteService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.vote.register}"})
    public void consume(VoteRequestDTO vote) throws EncryptionException {
        voteService.saveVote(vote);
    }
}
