package com.aernaur.votingSystem.dto;

import com.aernaur.votingSystem.entity.SubElection;
import com.aernaur.votingSystem.entity.types.SubElectionTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SubElectionDTO {

    private UUID id;

    @NotNull
    private String position;

    @NotNull
    private SubElectionTypeEnum type;

    public SubElectionDTO(SubElection subElection) {
        this.id = subElection.getId();
        this.position = subElection.getPosition();
        this.type = subElection.getType();
    }
}
