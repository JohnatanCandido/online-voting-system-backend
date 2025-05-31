package com.aernaur.votingSystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDTO implements Serializable {

    private UUID id;

    @NotNull
    private UUID personId;

    @NotNull
    private UUID partyId;

    @NotNull
    private Integer number;

    @NotNull
    private UUID subElectionId;

    private String candidateName;
    private String partyName;
    private String profilePicS3Name;

    public CandidateDTO(UUID id,
                        Integer number,
                        String candidateName,
                        String partyName) {
        this.id = id;
        this.number = number;
        this.candidateName = candidateName;
        this.partyName = partyName;
    }

    public CandidateDTO(UUID id,
                        Integer number,
                        String candidateName,
                        String partyName,
                        String profilePicS3Name) {
        this.id = id;
        this.number = number;
        this.candidateName = candidateName;
        this.partyName = partyName;
        this.profilePicS3Name = profilePicS3Name;
    }
}
