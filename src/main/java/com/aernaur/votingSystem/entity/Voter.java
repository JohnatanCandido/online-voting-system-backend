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
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "voter")
public class Voter implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "sub_election_id", nullable = false)
    private SubElection subElection;

    @Column(name = "vote_computed")
    private LocalDateTime voteComputed;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Voter voter = (Voter) o;
        return Objects.equals(id, voter.id)
                && Objects.equals(person, voter.person)
                && Objects.equals(subElection, voter.subElection)
                && Objects.equals(voteComputed, voter.voteComputed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, subElection, voteComputed);
    }
}
