package com.aernaur.votingSystem.service.queue;

import com.aernaur.votingSystem.dto.VoteRequestDTO;
import com.aernaur.votingSystem.entity.EncryptedVote;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QueueManager {

    @Value("${rabbitmq.exchange.name}")
    private String topicExchangeName;

    @Value("${rabbitmq.routing.vote.register}")
    private String registerVoteRoutingKey;

    @Value("${rabbitmq.routing.vote.count}")
    private String countVotesRoutingKey;

    @Value("${rabbitmq.routing.vote.decrypt}")
    private String decryptVoteRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public QueueManager(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendRegisterVote(VoteRequestDTO vote) {
        rabbitTemplate.convertAndSend(topicExchangeName, registerVoteRoutingKey, vote);
    }

    public void sendCountVotes(UUID subElectionId) {
        rabbitTemplate.convertAndSend(topicExchangeName, countVotesRoutingKey, subElectionId);
    }

    public void sendDecryptAndSaveVote(EncryptedVote encryptedVote) {
        rabbitTemplate.convertAndSend(topicExchangeName, decryptVoteRoutingKey, encryptedVote);
    }
}
