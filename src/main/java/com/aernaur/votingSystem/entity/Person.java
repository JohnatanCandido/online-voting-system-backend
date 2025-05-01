package com.aernaur.votingSystem.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "person")
public class Person implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "profile_pic_url")
    private String profilePicS3Name;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Login login;

    public Person(UUID id) {
        this.id = id;
    }
}
