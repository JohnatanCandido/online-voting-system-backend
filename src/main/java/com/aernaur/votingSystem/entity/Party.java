package com.aernaur.votingSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "party")
public class Party implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String acronym;

    private Integer number;

    public Party(UUID id) {
        this.id = id;
    }
}
