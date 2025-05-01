package com.aernaur.votingSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "login")
public class Login {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    private String password;

    private boolean admin;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Login(Person person) {
        this.person = person;
    }
}