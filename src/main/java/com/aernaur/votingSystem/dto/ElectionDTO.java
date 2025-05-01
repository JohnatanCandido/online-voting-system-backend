package com.aernaur.votingSystem.dto;

import com.aernaur.votingSystem.entity.Election;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElectionDTO implements Serializable {

    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private LocalDate start;

    @NotNull
    private LocalDate end;

    private List<SubElectionDTO> subElections;

    public ElectionDTO(Election election) {
        this.id = election.getId();
        this.name = election.getName();
        this.description = election.getDescription();
        this.start = election.getStartDate();
        this.end = election.getEndDate();
    }
}
