package com.aernaur.votingSystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "vote")
public class Vote implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "encrypted_vote_id", nullable = false)
    private UUID encryptedVoteId;

    @Column(name = "sub_election_id", nullable = false)
    private UUID subElectionId;

    @Column(name = "candidate_id")
    private UUID candidateId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(id, vote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
