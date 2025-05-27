package com.aernaur.votingSystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "encrypted_vote")
public class EncryptedVote implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "sub_election_id")
    private UUID subElectionId;

    @Column(name = "encrypted_data")
    private String encryptedData;
}
