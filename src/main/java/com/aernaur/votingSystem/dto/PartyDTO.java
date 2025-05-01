package com.aernaur.votingSystem.dto;

import com.aernaur.votingSystem.entity.Party;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PartyDTO implements Serializable {

    private UUID partyId;

    @NotNull
    private String partyName;

    @NotNull
    private String partyAcronym;

    @NotNull
    private Integer partyNumber;

    public PartyDTO(Party party) {
        this.partyId = party.getId();
        this.partyName = party.getName();
        this.partyAcronym = party.getAcronym();
        this.partyNumber = party.getNumber();
    }
}
