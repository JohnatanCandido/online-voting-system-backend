package com.aernaur.votingSystem.service.queue.consumer;

import com.aernaur.votingSystem.service.ElectionVoteCountingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CountVotesConsumer {

    private final ElectionVoteCountingService electionVoteCountingService;

    public CountVotesConsumer(ElectionVoteCountingService electionVoteCountingService) {
        this.electionVoteCountingService = electionVoteCountingService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.vote.count}"})
    public void consume(UUID subElectionId) {
        electionVoteCountingService.countVotes(subElectionId);
    }
}
