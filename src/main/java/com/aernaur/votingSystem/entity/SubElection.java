package com.aernaur.votingSystem.entity;

import com.aernaur.votingSystem.entity.types.SubElectionTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sub_election")
public class SubElection implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "position_name")
    private String position;

    private SubElectionTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    public SubElection(UUID id) {
        this.id = id;
    }

    public SubElection(Election election) {
        this.election = election;
    }
}
