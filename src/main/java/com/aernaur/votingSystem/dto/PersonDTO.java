package com.aernaur.votingSystem.dto;

import com.aernaur.votingSystem.entity.Person;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
public class PersonDTO implements Serializable {

    private UUID personId;

    @NotBlank
    private String personName;

    @Past
    @NotNull
    private LocalDate birthDate;

    private Long currentPartyId;

    private String profilePicUrl;

    private boolean admin;

    public PersonDTO() {}

    public PersonDTO(Person person) {
        this.personId = person.getId();
        this.personName = person.getName();
        this.birthDate = person.getBirthDate();
    }
}
